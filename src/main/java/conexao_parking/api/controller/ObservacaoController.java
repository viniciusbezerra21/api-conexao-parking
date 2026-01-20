package conexao_parking.api.controller;

import conexao_parking.api.observacao.DadosCadastroObservacao;
import conexao_parking.api.observacao.DadosListagemObservacao;
import conexao_parking.api.observacao.Observacao;
import conexao_parking.api.observacao.ObservacaoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("observacao")
public class ObservacaoController {
    @Autowired
    private ObservacaoRepository repository;


    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroObservacao dados) {
        repository.save(new Observacao(dados));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemObservacao>> listar(@PageableDefault(size = 10)Pageable paginacao) {
        var page = repository.findAll(paginacao).map(DadosListagemObservacao::new);
        return ResponseEntity.ok(page);
    }
}
