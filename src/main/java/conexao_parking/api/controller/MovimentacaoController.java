package conexao_parking.api.controller;

import conexao_parking.api.movimentacao.DadosCadastroMovimentacao;
import conexao_parking.api.movimentacao.MoviementacaoService;
import conexao_parking.api.movimentacao.Movimentacao;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movimentacao")
public class MovimentacaoController {

    private final MoviementacaoService service;

    public MovimentacaoController(MoviementacaoService service) {
        this.service = service;
    }

    @PostMapping
    @Transactional
    public void Cadastrar(@RequestBody @Valid DadosCadastroMovimentacao dados) {
        service.cadastrar(dados);
    }



}
