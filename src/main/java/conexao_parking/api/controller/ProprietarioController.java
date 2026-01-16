package conexao_parking.api.controller;


import conexao_parking.api.proprietario.DadosAtualizacaoProprietario;
import conexao_parking.api.proprietario.DadosCadastroProprietario;
import conexao_parking.api.proprietario.Proprietario;
import conexao_parking.api.proprietario.ProprietarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("proprietario")
public class ProprietarioController {

    @Autowired
    private ProprietarioRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroProprietario dados) {
        repository.save(new Proprietario(dados));
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoProprietario dados) {
        var proprietario = repository.getReferenceById(dados.id_proprietario());
        proprietario.atualizarInformacoes(dados);
    }
}
