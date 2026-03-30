package conexao_parking.api.controller;

import conexao_parking.api.domain.proprietario.DadosCadastroProprietario;
import conexao_parking.api.domain.proprietario.Proprietario;
import conexao_parking.api.domain.veiculo.*;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class VeiculoControllerTest {

    @MockitoBean
    private VeiculoService service;

    @Autowired
    private JacksonTester<DadosCadastroVeiculo> jsonCadastroVeiculo;

    @Autowired
    private JacksonTester<DadosCadastroProprietario> jsonCadastroProprietario;

    @MockitoBean
    private VeiculoRepository repository;

    @Autowired
    private VeiculoController controller;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Mockito.reset(service, repository);
    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar 400 quando campos obrigatórios estão ausentes")
    void cadastrar_campos_invalidos() throws Exception {

        String jsonInvalido = "{}";

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/veiculo")
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                                .content(jsonInvalido)
                )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isBadRequest());
    }

    @WithMockUser
    @DisplayName("Verifica que ao cadastrar um veículo o controller responde com 201 e Location apontando para /veiculo/{id}")
    @Test
    void cadastrar() {
        var veiculo = Mockito.mock(Veiculo.class);
        Mockito.when(veiculo.getIdVeiculo()).thenReturn(1L);

        var dados = Mockito.mock(DadosCadastroVeiculo.class);
        Mockito.when(service.cadastrar(any())).thenReturn(veiculo);

        var uriBuilder = UriComponentsBuilder.fromUriString("http://localhost");

        ResponseEntity<?> resp = controller.cadastrar(dados, uriBuilder);

        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertNotNull(resp.getHeaders().getLocation());
        assertTrue(resp.getHeaders().getLocation().toString().endsWith("/veiculo/1"));
        assertNotNull(resp.getBody());
    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar 409 quando o Service lança DataIntegrityViolationException (Duplicidade)")
    void cadastrar_erro_duplicidade() throws Exception {

        Mockito.when(service.cadastrar(any()))
                .thenThrow(new org.springframework.dao.DataIntegrityViolationException("Duplicate entry '140.101.969-20' for key 'cpf_proprietario'"));

        var proprietario = new DadosCadastroProprietario("Dono", "14010196920");
        var dados = new DadosCadastroVeiculo("ABC1D23", "Preto", true, TipoVeiculo.CARRO, StatusVeiculo.ATIVO, proprietario, "Empresa");

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/veiculo")
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                                .content(jsonCadastroVeiculo.write(dados).getJson())
                )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isConflict());
    }

    @WithMockUser
    @DisplayName("Verifica listagem em dashboard retornando página com elementos não excluídos")
    @Test
    void listagemDashboard() {
        var veiculo = Mockito.mock(Veiculo.class);
        var page = new PageImpl<>(List.of(veiculo));

        Mockito.when(repository.findByExcluidoFalse(any(Pageable.class))).thenReturn(page);

        ResponseEntity<?> resp = controller.listagemDashboard(PageRequest.of(0, 10));

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
        var body = (org.springframework.data.domain.Page<?>) resp.getBody();
        assertEquals(1, body.getTotalElements());
    }

    @WithMockUser
    @DisplayName("Verifica detalhamento retornando 200 e corpo preenchido")
    @Test
    void detalhar() {
        var veiculo = Mockito.mock(Veiculo.class);
        Mockito.when(repository.getReferenceById(1L)).thenReturn(veiculo);

        ResponseEntity<?> resp = controller.detalhar(1L);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
    }

    @WithMockUser
    @DisplayName("Verifica atualização chamando repository.getReferenceById e retornando 200")
    @Test
    void atualizar() {
        var veiculo = Mockito.mock(Veiculo.class);
        var dados = Mockito.mock(DadosAtualizacaoVeiculo.class);
        Mockito.when(dados.idVeiculo()).thenReturn(1L);
        Mockito.when(repository.getReferenceById(1L)).thenReturn(veiculo);

        ResponseEntity<?> resp = controller.atualizar(dados);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
    }

    @WithMockUser
    @DisplayName("Verifica bloqueio do veículo e mensagem de sucesso no corpo")
    @Test
    void bloquear() {
        var veiculo = Mockito.mock(Veiculo.class);
        Mockito.when(repository.getReferenceById(1L)).thenReturn(veiculo);

        ResponseEntity<?> resp = controller.bloquear(1L);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
        assertTrue(resp.getBody().toString().contains("Veículo bloqueado com sucesso"));
    }

    @WithMockUser
    @DisplayName("Verifica desbloqueio do veículo e mensagem de sucesso no corpo")
    @Test
    void desbloquear() {
        var veiculo = Mockito.mock(Veiculo.class);
        Mockito.when(repository.getReferenceById(1L)).thenReturn(veiculo);

        ResponseEntity<?> resp = controller.desbloquear(1L);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
        assertTrue(resp.getBody().toString().contains("Veículo desbloqueado com sucesso"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve permitir exclusão quando o usuário é ADMIN")
    void excluirSucesso() throws Exception {

        var veiculo = Mockito.mock(Veiculo.class);
        Mockito.when(repository.getReferenceById(1L)).thenReturn(veiculo);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .delete("/veiculo/1"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .status().isNoContent());

        // Verifica se o método de exclusão lógica foi mesmo chamado
        Mockito.verify(veiculo).excluir();
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Deve barrar com 403 quando o usuário não é ADMIN")
    void excluirBarrado() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .delete("/veiculo/1"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .status().isForbidden());


        Mockito.verifyNoInteractions(service);
    }




    public record DadosListagemVeiculoDashboard(
            String nome,
            String cpf_proprietario,
            TipoVeiculo tipo_veiculo,
            String numero_placa,
            String cor,
            StatusVeiculo status,
            Boolean bloqueado
    ) {
        public DadosListagemVeiculoDashboard(Veiculo veiculo) {
            this(
                    veiculo != null && veiculo.getProprietario() != null && veiculo.getProprietario().getNome() != null
                            ? veiculo.getProprietario().getNome() : "",
                    veiculo != null && veiculo.getProprietario() != null && veiculo.getProprietario().getCpfProprietario() != null
                            ? veiculo.getProprietario().getCpfProprietario() : "",
                    veiculo != null ? veiculo.getTipoVeiculo() : null,
                    veiculo != null && veiculo.getNumeroPlaca() != null ? veiculo.getNumeroPlaca() : "",
                    veiculo != null && veiculo.getCor() != null ? veiculo.getCor() : "",
                    veiculo != null ? veiculo.getStatus() : null,
                    veiculo != null ? veiculo.getBloqueado() : Boolean.FALSE
            );
        }
    }

    public record DadosDetalhamentoVeiculo(
            Long idVeiculo,
            String nomeProprietario,
            String cpfProprietario,
            TipoVeiculo tipoVeiculo,
            String placa,
            String cor,
            StatusVeiculo status,
            Boolean bloqueado
    ) {
        public DadosDetalhamentoVeiculo(Veiculo veiculo) {
            this(
                    veiculo != null ? veiculo.getIdVeiculo() : null,
                    veiculo != null && veiculo.getProprietario() != null && veiculo.getProprietario().getNome() != null
                            ? veiculo.getProprietario().getNome() : "",
                    veiculo != null && veiculo.getProprietario() != null && veiculo.getProprietario().getCpfProprietario() != null
                            ? veiculo.getProprietario().getCpfProprietario() : "",
                    veiculo != null ? veiculo.getTipoVeiculo() : null,
                    veiculo != null && veiculo.getNumeroPlaca() != null ? veiculo.getNumeroPlaca() : "",
                    veiculo != null && veiculo.getCor() != null ? veiculo.getCor() : "",
                    veiculo != null ? veiculo.getStatus() : null,
                    veiculo != null ? veiculo.getBloqueado() : Boolean.FALSE
            );
        }
    }

    private Veiculo createVeiculoMockWithProprietario() {
        var veiculo = Mockito.mock(Veiculo.class);
        var proprietario = Mockito.mock(Proprietario.class);

        Mockito.when(proprietario.getNome()).thenReturn("Proprietario Teste");
        Mockito.when(proprietario.getCpfProprietario()).thenReturn("00000000000");

        Mockito.when(veiculo.getProprietario()).thenReturn(proprietario);
        Mockito.when(veiculo.getTipoVeiculo()).thenReturn(null);
        Mockito.when(veiculo.getNumeroPlaca()).thenReturn("ABC1234");
        Mockito.when(veiculo.getCor()).thenReturn("Preto");
        Mockito.when(veiculo.getStatus()).thenReturn(null);
        Mockito.when(veiculo.getBloqueado()).thenReturn(false);
        Mockito.when(veiculo.getIdVeiculo()).thenReturn(1L);

        return veiculo;
    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar 400 ao tentar enviar HTML/Script no campo cor (Anti-XSS)")
    void cadastrar_cenario_xss() throws Exception {
        var proprietario = new DadosCadastroProprietario("Dono Teste", "12345678909"); // CPF fake para este teste
        var dadosMaliciosos = new DadosCadastroVeiculo(
                "ABC1234",
                "<script>alert('xss')</script>", // Tenta injetar script
                true,
                TipoVeiculo.CARRO,
                StatusVeiculo.ATIVO,
                proprietario,
                "Empresa"
        );

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/veiculo")
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                                .content(jsonCadastroVeiculo.write(dadosMaliciosos).getJson())
                )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar 400 quando o CPF do proprietário é numericamente inválido")
    void cadastrar_cpf_invalido() throws Exception {
        var proprietarioInvalido = new DadosCadastroProprietario("Dono", "11111111111"); // CPF de números repetidos é inválido
        var dados = new DadosCadastroVeiculo("ABC1234", "Preto", true, TipoVeiculo.CARRO, StatusVeiculo.ATIVO, proprietarioInvalido, "Empresa");

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/veiculo")
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                                .content(jsonCadastroVeiculo.write(dados).getJson())
                )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isBadRequest());
    }

}
