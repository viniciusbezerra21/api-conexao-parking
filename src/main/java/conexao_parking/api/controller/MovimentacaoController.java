package conexao_parking.api.controller;

import conexao_parking.api.movimentacao.DadosCadastroMovimentacao;
import conexao_parking.api.movimentacao.DadosListagemMovimentacao;
import conexao_parking.api.movimentacao.MoviementacaoService;
import conexao_parking.api.movimentacao.MovimentacaoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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
    public void Cadastrar(@RequestBody @Valid DadosCadastroMovimentacao dados) {
        service.cadastrar(dados);
    }

    @GetMapping
    public Page<DadosListagemMovimentacao> listar(@PageableDefault(size = 10)Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemMovimentacao::new);
    }

}
