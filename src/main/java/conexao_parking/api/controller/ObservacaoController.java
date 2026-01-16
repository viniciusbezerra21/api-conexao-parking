package conexao_parking.api.controller;

import conexao_parking.api.observacao.DadosCadastroObservacao;
import conexao_parking.api.observacao.Observacao;
import conexao_parking.api.observacao.ObservacaoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
