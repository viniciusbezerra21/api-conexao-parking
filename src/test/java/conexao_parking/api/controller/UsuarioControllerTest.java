// language: java
// src/test/java/conexao_parking/api/controller/UsuarioControllerTest.java
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class UsuarioControllerTest {

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
        // injeta repository e tokenService via reflection (campos privados autowired no controller)
        Field repoField = UsuarioController.class.getDeclaredField("repository");
        repoField.setAccessible(true);
        repoField.set(controller, repository);

        Field tokenField = UsuarioController.class.getDeclaredField("tokenService");
        tokenField.setAccessible(true);
        tokenField.set(controller, tokenService);
    }

    private Usuario createUsuarioMock() {
        var usuario = Mockito.mock(Usuario.class);
        Mockito.when(usuario.getId_usuario()).thenReturn(1L);
        Mockito.when(usuario.getEmailCorporativo()).thenReturn("usuario@empresa.com");
        return usuario;
    }

    @WithMockUser
    @DisplayName("Verifica que ao cadastrar um usuário o controller responde com 201 e Location apontando para /usuario/{id}")
    @Test
    void cadastrar() {
        var usuario = createUsuarioMock();
        var dados = Mockito.mock(DadosCadastroUsuario.class);

        Mockito.when(service.cadastrar(any())).thenReturn(usuario);
        Mockito.when(tokenService.gerarToken(usuario)).thenReturn("token-jwt");

        var uriBuilder = UriComponentsBuilder.fromUriString("http://localhost");

        ResponseEntity<?> resp = controller.cadastrar(dados, uriBuilder);

        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertNotNull(resp.getHeaders().getLocation());
        assertTrue(resp.getHeaders().getLocation().toString().endsWith("/usuario/1"));
        assertNotNull(resp.getBody());
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
}