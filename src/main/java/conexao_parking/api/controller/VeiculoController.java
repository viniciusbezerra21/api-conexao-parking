package conexao_parking.api.controller;


import conexao_parking.api.domain.veiculo.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("veiculo")
public class VeiculoController {

    private final VeiculoService service;
    private VeiculoRepository repository;

    public VeiculoController(VeiculoService service, VeiculoRepository repository) {
        this.service = service;
        this.repository = repository;
    }
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroVeiculo dados, UriComponentsBuilder uriBuilder) {
        Veiculo veiculo = service.cadastrar(dados);

        var uri = uriBuilder.path("/veiculo/{id}").buildAndExpand(veiculo.getIdVeiculo()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoVeiculo(veiculo));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemVeiculoDashboard>> listagemDashboard(@PageableDefault(size = 10, sort = {"proprietario.nome"})Pageable paginacao) {
        var page = repository.findByExcluidoFalse(paginacao).map(DadosListagemVeiculoDashboard::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoVeiculo> detalhar(@PathVariable Long id) {
        var veiculo = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoVeiculo(veiculo));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoVeiculo dados) {
        var veiculo = repository.getReferenceById(dados.id_veiculo());
        veiculo.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosAtualizacaoVeiculo(veiculo));
    }

    @PatchMapping("/veiculo/{id}/bloquear")
    public ResponseEntity bloquear(@PathVariable Long id) {
        var veiculo = repository.getReferenceById(id);
        veiculo.bloquear();

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/veiculo/{id}/desbloquear")
    public ResponseEntity desbloquear(@PathVariable Long id) {
        var veiculo = repository.getReferenceById(id);
        veiculo.bloquear();

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var veiculo = repository.getReferenceById(id);
        veiculo.excluir();

        return ResponseEntity.noContent().build();
    }
}

