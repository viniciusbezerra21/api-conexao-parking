package conexao_parking.api.controller;


import conexao_parking.api.veiculo.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public Page<DadosListagemVeiculoDashboard> listagemDashboard(@PageableDefault(size = 10, sort = {"proprietario.nome"})Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemVeiculoDashboard::new);
    }

}

