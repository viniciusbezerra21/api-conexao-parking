package conexao_parking.api.controller;

import conexao_parking.api.movimentacao.*;
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
@RequestMapping("movimentacao")
public class MovimentacaoController {

    private final MoviementacaoService service;
    @Autowired
    private MovimentacaoRepository repository;

    public MovimentacaoController(MoviementacaoService service) {
        this.service = service;
    }

    @PostMapping
    @Transactional
    public ResponseEntity Cadastrar(@RequestBody @Valid DadosCadastroMovimentacao dados, UriComponentsBuilder uriBuilder) {
        Movimentacao movimentacao = service.cadastrar(dados);

        var uri = uriBuilder.path("/movimentacao/{id}").buildAndExpand(movimentacao.getId_movimentacao()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMovimentacao(movimentacao));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMovimentacao>> listar(@PageableDefault(size = 10)Pageable paginacao) {
        var page = repository.findAll(paginacao).map(DadosListagemMovimentacao::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMovimentacao> detalhar(@PathVariable Long id) {
        var movimentacao = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMovimentacao(movimentacao));
    }


}
