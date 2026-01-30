package conexao_parking.api.controller;

import conexao_parking.api.domain.movimentacao.*;
import conexao_parking.api.domain.veiculo.Veiculo;
import conexao_parking.api.domain.proprietario.Proprietario;
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
class MovimentacaoControllerTest {

    @Autowired
    @MockitoBean
    private MovimentacaoService service;

    @Autowired
    @MockitoBean
    private MovimentacaoRepository repository;

    private MovimentacaoController controller;

    @BeforeEach
    void setUp() {
        controller = new MovimentacaoController(service, repository);
    }

    private Movimentacao createMovimentacaoMockWithVeiculoAndProprietario() {
        var movimentacao = Mockito.mock(Movimentacao.class);
        var veiculo = Mockito.mock(Veiculo.class);
        var proprietario = Mockito.mock(Proprietario.class);

        Mockito.when(proprietario.getNome()).thenReturn("Proprietario Teste");
        Mockito.when(proprietario.getCpf_proprietario()).thenReturn("00000000000");

        Mockito.when(veiculo.getProprietario()).thenReturn(proprietario);
        Mockito.when(veiculo.getNumero_placa()).thenReturn("ABC1234");
        Mockito.when(veiculo.getCor()).thenReturn("Preto");

        Mockito.when(movimentacao.getId()).thenReturn(1L);
        Mockito.when(movimentacao.getVeiculo()).thenReturn(veiculo);
        // removida a linha que fazia Mockito.when(movimentacao.getVeiculo()).thenReturn(null);
        Mockito.when(movimentacao.getDataEntrada()).thenReturn(null);
        Mockito.when(movimentacao.getDataSaida()).thenReturn(null);

        return movimentacao;
    }


    @WithMockUser
    @DisplayName("Verifica que ao liberar entrada o controller responde com 201 e Location apontando para /movimentacao/{id}")
    @Test
    void liberarEntrada() {
        var movimentacao = createMovimentacaoMockWithVeiculoAndProprietario();
        var dados = Mockito.mock(DadosMovimentacaoEntrada.class);
        Mockito.when(service.liberarEntrada(any())).thenReturn(movimentacao);

        var uriBuilder = UriComponentsBuilder.fromUriString("http://localhost");

        ResponseEntity<?> resp = controller.liberarEntrada(dados, uriBuilder);

        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertNotNull(resp.getHeaders().getLocation());
        assertTrue(resp.getHeaders().getLocation().toString().endsWith("/movimentacao/1"));
        assertNotNull(resp.getBody());
    }

    @WithMockUser
    @DisplayName("Verifica que ao liberar saida o controller responde com 201 e Location apontando para /movimentacao/{id}")
    @Test
    void liberarSaida() {
        var movimentacao = createMovimentacaoMockWithVeiculoAndProprietario();
        var dados = Mockito.mock(DadosMovimentacaoSaida.class);
        Mockito.when(service.liberarSaida(any())).thenReturn(movimentacao);

        var uriBuilder = UriComponentsBuilder.fromUriString("http://localhost");

        ResponseEntity<?> resp = controller.liberarSaida(dados, uriBuilder);

        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertNotNull(resp.getHeaders().getLocation());
        assertFalse(resp.getHeaders().getLocation().toString().endsWith("/movimentacao/1"));
        assertNotNull(resp.getBody());
    }

    @WithMockUser
    @DisplayName("Verifica listagem retornando página com movimentações")
    @Test
    void listar() {
        var movimentacao = createMovimentacaoMockWithVeiculoAndProprietario();
        var page = new PageImpl<>(List.of(movimentacao));

        Mockito.when(repository.findAll(any(Pageable.class))).thenReturn(page);

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
        var movimentacao = createMovimentacaoMockWithVeiculoAndProprietario();
        Mockito.when(repository.getReferenceById(1L)).thenReturn(movimentacao);

        ResponseEntity<?> resp = controller.detalhar(1L);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
    }
}