package conexao_parking.api.controller;


import conexao_parking.api.proprietario.DadosCadastroProprietario;
import conexao_parking.api.proprietario.Proprietario;
import conexao_parking.api.proprietario.ProprietarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
