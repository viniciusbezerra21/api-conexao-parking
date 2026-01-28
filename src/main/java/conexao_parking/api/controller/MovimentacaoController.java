package conexao_parking.api.controller;

import conexao_parking.api.domain.movimentacao.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("movimentacao")
public class MovimentacaoController {


    private final MovimentacaoService service;

    @Autowired
    private MovimentacaoRepository repository;



    public MovimentacaoController(MovimentacaoService service, MovimentacaoRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/liberar-entrada")
    @Transactional
    public ResponseEntity liberarEntrada(@RequestBody @Valid DadosMovimentacaoEntrada dados, UriComponentsBuilder uriBuilder) {
        Movimentacao movimentacao = service.liberarEntrada(dados);

        var uri = uriBuilder.path("/movimentacao/{id}").buildAndExpand(movimentacao.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMovimentacao(movimentacao));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/liberar-saida")
    @Transactional
    public ResponseEntity liberarSaida(@RequestBody @Valid DadosMovimentacaoSaida dados, UriComponentsBuilder uriBuilder) {
        Movimentacao movimentacao = service.liberarSaida(dados);

        var uri = uriBuilder.path("/movimentacao/{id}").buildAndExpand(movimentacao).toUri();

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
