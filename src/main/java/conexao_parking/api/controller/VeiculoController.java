package conexao_parking.api.controller;


import conexao_parking.api.veiculo.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("veiculo")
public class VeiculoController {
    private final VeiculoService service;

    @Autowired
    private VeiculoRepository repository;

    public VeiculoController(VeiculoService service) {
        this.service = service;

    }
    @PostMapping
    @Transactional
    public void Cadastrar(@RequestBody @Valid DadosCadastroVeiculo dados) {
        service.cadastrar(dados);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemVeiculoDashboard>> listagemDashboard(@PageableDefault(size = 10, sort = {"proprietario.nome"})Pageable paginacao) {
        return repository.findByExcluidoFalse(paginacao).map(DadosListagemVeiculoDashboard::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoVeiculo dados) {
        var veiculo = repository.getReferenceById(dados.id_veiculo());
        veiculo.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        var veiculo = repository.getReferenceById(id);
        veiculo.excluir();
    }
}

