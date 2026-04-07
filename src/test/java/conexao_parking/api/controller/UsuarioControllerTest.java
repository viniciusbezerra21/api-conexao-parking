package conexao_parking.api.controller;

import conexao_parking.api.domain.usuario.*;
import conexao_parking.api.infra.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosCadastroUsuario> jsonCadastro;

    @Autowired
    private JacksonTester<DadosAtualizacaoUsuario> jsonAtualizacao;

    @Autowired
    @MockitoBean
    private UsuarioService service;

    @Autowired
    @MockitoBean
    private UsuarioRepository repository;

    @Autowired
    @MockitoBean
    private TokenService tokenService;

    private UsuarioController controller;

    @BeforeEach
    void setUp() throws Exception {
        controller = new UsuarioController(service);

        Field repoField = UsuarioController.class.getDeclaredField("repository");
        repoField.setAccessible(true);
        repoField.set(controller, repository);

        Field tokenField = UsuarioController.class.getDeclaredField("tokenService");
        tokenField.setAccessible(true);
        tokenField.set(controller, tokenService);
    }

    private Usuario createUsuarioMock() {
        var usuario = Mockito.mock(Usuario.class);
        Mockito.when(usuario.getIdUsuario()).thenReturn(1L);
        Mockito.when(usuario.getEmailCorporativo()).thenReturn("usuario@empresa.com");
        return usuario;
    }


    @DisplayName("Deve retornar 403 Forbidden para cadastro sem role ADMIN")
    @Test
    @WithMockUser(roles = {"USER"})
    void cadastrar_semAdmin() throws Exception {
        // O DTO agora só recebe o e-mail no construtor (ou via Record)
        var dados = new DadosCadastroUsuario("usuario@empresa.com");

        mockMvc.perform(
                        post("/usuario/cadastro")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonCadastro.write(dados).getJson())
                )
                .andExpect(status().isForbidden());
    }

    @DisplayName("Verifica que ao cadastrar um usuário o controller responde com 201 e retorna o Token")
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void cadastrar() throws Exception {

        var dados = new DadosCadastroUsuario("novo.usuario@empresa.com");
        var usuario = createUsuarioMock(); // ID 1, email "usuario@empresa.com"


        Mockito.when(service.cadastrar(any(DadosCadastroUsuario.class))).thenReturn(usuario);
        Mockito.when(tokenService.gerarToken(any(Usuario.class))).thenReturn("token-jwt-gerado");


        mockMvc.perform(
                        post("/usuario/cadastro")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonCadastro.write(dados).getJson())
                )
                .andExpect(status().isCreated()) // Espera 201
                .andExpect(header().string("Location", "http://localhost/usuario/1")) // Valida a URI
                .andExpect(jsonPath("$.token").value("token-jwt-gerado")) // Valida se o token veio no corpo
                .andExpect(jsonPath("$.usuario.idUsuario").value(1)); // Valida dados do usuário no corpo
    }

    @WithMockUser
    @DisplayName("Verifica listagem retornando página com usuários ativos")
    @Test
    void listar() {
        var usuario = createUsuarioMock();
        var page = new PageImpl<>(List.of(usuario));

        Mockito.when(repository.findAllByAtivoTrue(any(Pageable.class))).thenReturn(page);

        ResponseEntity<?> resp = controller.listar(PageRequest.of(0, 10));

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
        var body = (org.springframework.data.domain.Page<?>) resp.getBody();
        assertEquals(1, body.getTotalElements());
    }

    @WithMockUser
    @DisplayName("Verifica detalhamento retornando 200 e corpo preenchido")
    @Test
    void detalhar() {
        var usuario = createUsuarioMock();
        Mockito.when(repository.getReferenceById(1L)).thenReturn(usuario);

        ResponseEntity<?> resp = controller.detalhar(1L);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
    }

    @Test
    @DisplayName("Deve resetar a senha para o padrão e retornar 200 OK")
    @WithMockUser(roles = {"ADMIN"})
    void resetarSenhaCenario1() throws Exception {

        var usuario = createUsuarioMock();
        Mockito.when(repository.getReferenceById(1L)).thenReturn(usuario);


        mockMvc.perform(put("/usuario/1/resetar-senha"))
                .andExpect(status().isOk());

        Mockito.verify(usuario).setSenha(any());
        Mockito.verify(usuario).setPrecisaTrocarSenha(true);
    }

    @Test
    @DisplayName("Deve retornar 403 ao tentar resetar senha sem ser ADMIN")
    @WithMockUser(roles = {"USER"})
    void resetarSenhaCenario2() throws Exception {
        mockMvc.perform(put("/usuario/1/resetar-senha"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Verifica atualização com nova senha e retorno do DTO correto")
    @WithMockUser
    void atualizarComSenha() throws Exception {

        Long id = 1L;

        var dados = new DadosAtualizacaoUsuario(id, "novo@email.com", "NovaSenha123");
        var usuarioLogado = createUsuarioMock();
        var usuarioAtualizado = createUsuarioMock();

        Mockito.when(usuarioAtualizado.getIdUsuario()).thenReturn(id);
        Mockito.when(usuarioAtualizado.getEmailCorporativo()).thenReturn("novo@email.com");
        Mockito.when(service.atualizar(any(), any(), any())).thenReturn(usuarioAtualizado);


        mockMvc.perform(
                        put("/usuario/" + id) // Verifique se o path é este mesmo
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonAtualizacao.write(dados).getJson())
                )
                .andExpect(status().isOk());
    }


    @WithMockUser
    @DisplayName("Verifica atualização chamando service.atualizar e retornando 200")
    @Test
    void atualizar() {
        var dados = Mockito.mock(DadosAtualizacaoUsuario.class);
        var usuarioLogado = createUsuarioMock();
        var usuarioAtualizado = createUsuarioMock();

        Mockito.when(service.atualizar(1L, dados, usuarioLogado)).thenReturn(usuarioAtualizado);

        ResponseEntity<?> resp = controller.atualizar(1L, dados, usuarioLogado);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
    }

    @WithMockUser
    @DisplayName("Verifica exclusão lógica do usuário e retorno 204 No Content")
    @Test
    void excluir() {
        var usuario = createUsuarioMock();
        Mockito.when(repository.getReferenceById(1L)).thenReturn(usuario);

        ResponseEntity<?> resp = controller.excluir(1L);

        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
    }

    @WithMockUser
    @DisplayName("Verifica tornarAdmin e retorno 204 No Content")
    @Test
    void tornarAdmin() {
        var usuario = createUsuarioMock();
        Mockito.when(repository.getReferenceById(1L)).thenReturn(usuario);

        ResponseEntity<?> resp = controller.tornarAdmin(1L);

        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
    }

    @Test
    @DisplayName("Deve alterar role para USER e retornar 204 No Content quando logado como ADMIN")
    @WithMockUser(roles = {"ADMIN"})
    void tornarUsuarioCenario1() throws Exception {
        // ARRANGE
        Long id = 1L;
        var usuario = createUsuarioMock();
        Mockito.when(repository.getReferenceById(id)).thenReturn(usuario);

        // ACT & ASSERT
        mockMvc.perform(put("/usuario/{id}/tornar-usuario", id))
                .andExpect(status().isNoContent());

        // Verificações
        Mockito.verify(usuario, Mockito.times(1)).tornarUsuario();
        Mockito.verify(repository, Mockito.times(1)).save(usuario);
    }

    @Test
    @DisplayName("Deve retornar 403 Forbidden ao tentar tornar usuário comum sem role ADMIN")
    @WithMockUser(roles = {"USER"})
    void tornarUsuarioCenario2() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(put("/usuario/1/tornar-usuario"))
                .andExpect(status().isForbidden());

        // Garante que nem buscou no banco
        Mockito.verify(repository, Mockito.never()).getReferenceById(any());
    }

    @WithMockUser
    @DisplayName("Verifica tornarUsuario chamando o método da entidade e retornando 204 (Teste de Unidade)")
    @Test
    void tornarUsuarioUnidade() {
        // ARRANGE
        var usuario = createUsuarioMock();
        Mockito.when(repository.getReferenceById(1L)).thenReturn(usuario);

        // ACT
        ResponseEntity<?> resp = controller.tornarUsuario(1L);

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
        Mockito.verify(usuario).tornarUsuario();
    }

    @WithMockUser
    @DisplayName("Verifica disponibilidade de email retornando true quando disponível")
    @Test
    void verificarEmailDisponivel_true() {
        Mockito.when(service.emailDisponivel("teste@empresa.com")).thenReturn(true);

        ResponseEntity<Boolean> resp = controller.verificarEmailDisponivel("teste@empresa.com");

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertTrue(resp.getBody());
    }

    @WithMockUser
    @DisplayName("Verifica disponibilidade de email retornando false quando já existe")
    @Test
    void verificarEmailDisponivel_false() {
        Mockito.when(service.emailDisponivel("teste@empresa.com")).thenReturn(false);

        ResponseEntity<Boolean> resp = controller.verificarEmailDisponivel("teste@empresa.com");

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertFalse(resp.getBody());
    }
}