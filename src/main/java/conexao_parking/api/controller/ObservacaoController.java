package conexao_parking.api.controller;

import conexao_parking.api.observacao.*;
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
@RequestMapping("observacao")
public class ObservacaoController {
    @Autowired
    private ObservacaoRepository repository;


    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroObservacao dados, UriComponentsBuilder uriBuilder) {
        var observacao = new Observacao(dados);
        repository.save(observacao);

        var uri = uriBuilder.path("/observacao/{id}").buildAndExpand(observacao.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoObservacao(observacao));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemObservacao>> listar(@PageableDefault(size = 10)Pageable paginacao) {
        var page = repository.findAll(paginacao).map(DadosListagemObservacao::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoObservacao> detalhar(@PathVariable long id) {
        var observacao = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoObservacao(observacao));
    }
}
