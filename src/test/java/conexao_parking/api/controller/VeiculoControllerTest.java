// language: java
package conexao_parking.api.controller;

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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class VeiculoControllerTest {

    @Autowired
    @MockitoBean
    private VeiculoService service;

    @Autowired
    @MockitoBean
    private VeiculoRepository repository;

    @Autowired
    @MockitoBean
    private VeiculoController controller;

    @BeforeEach
    void setUp() {
        controller = new VeiculoController(service, repository);
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
        Mockito.when(dados.id_veiculo()).thenReturn(1L);
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

    @WithMockUser
    @DisplayName("Verifica exclusão lógica do veículo e retorno 204 No Content")
    @Test
    void excluir() {
        var veiculo = Mockito.mock(Veiculo.class);
        Mockito.when(repository.getReferenceById(1L)).thenReturn(veiculo);

        ResponseEntity<?> resp = controller.excluir(1L);

        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
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
                    veiculo != null && veiculo.getProprietario() != null && veiculo.getProprietario().getCpf_proprietario() != null
                            ? veiculo.getProprietario().getCpf_proprietario() : "",
                    veiculo != null ? veiculo.getTipo_veiculo() : null,
                    veiculo != null && veiculo.getNumero_placa() != null ? veiculo.getNumero_placa() : "",
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
                    veiculo != null && veiculo.getProprietario() != null && veiculo.getProprietario().getCpf_proprietario() != null
                            ? veiculo.getProprietario().getCpf_proprietario() : "",
                    veiculo != null ? veiculo.getTipo_veiculo() : null,
                    veiculo != null && veiculo.getNumero_placa() != null ? veiculo.getNumero_placa() : "",
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
        Mockito.when(proprietario.getCpf_proprietario()).thenReturn("00000000000");

        Mockito.when(veiculo.getProprietario()).thenReturn(proprietario);
        Mockito.when(veiculo.getTipo_veiculo()).thenReturn(null);
        Mockito.when(veiculo.getNumero_placa()).thenReturn("ABC1234");
        Mockito.when(veiculo.getCor()).thenReturn("Preto");
        Mockito.when(veiculo.getStatus()).thenReturn(null);
        Mockito.when(veiculo.getBloqueado()).thenReturn(false);
        Mockito.when(veiculo.getIdVeiculo()).thenReturn(1L);

        return veiculo;
    }

}
