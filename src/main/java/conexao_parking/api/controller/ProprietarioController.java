package conexao_parking.api.controller;


import conexao_parking.api.proprietario.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public Page<DadosListagemProprietario> listar(@PageableDefault(size = 10)Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemProprietario::new);
    }


    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoProprietario dados) {
        var proprietario = repository.getReferenceById(dados.id_proprietario());
        proprietario.atualizarInformacoes(dados);
    }


}
