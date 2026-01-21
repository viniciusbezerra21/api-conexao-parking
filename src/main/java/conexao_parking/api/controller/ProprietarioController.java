package conexao_parking.api.controller;


import conexao_parking.api.proprietario.*;
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
@RequestMapping("proprietario")
public class ProprietarioController {

    @Autowired
    private ProprietarioRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroProprietario dados, UriComponentsBuilder uriBuilder) {
        var proprietario = new Proprietario(dados);
        repository.save(proprietario);

        var uri = uriBuilder.path("/proprietario/{id}").buildAndExpand(proprietario.getId_proprietario()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoProprietario(proprietario));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemProprietario>> listar(@PageableDefault(size = 10)Pageable paginacao) {
        var page = repository.findAll(paginacao).map(DadosListagemProprietario::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoProprietario> detalhar(@PathVariable long id) {
        var proprietario = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoProprietario(proprietario));
    }


    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoProprietario dados) {
        var proprietario = repository.getReferenceById(dados.id_proprietario());
        proprietario.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosAtualizacaoProprietario(proprietario));
    }


}
