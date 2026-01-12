package conexao_parking.api.controller;


import conexao_parking.api.veiculo.DadosCadastroVeiculo;
import conexao_parking.api.veiculo.VeiculoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("veiculo")
public class VeiculoController {
    private final VeiculoService service;

    public VeiculoController(VeiculoService service) {
        this.service = service;

    }
    @PostMapping
    @Transactional
    public void Cadastrar(@RequestBody @Valid DadosCadastroVeiculo dados) {
        service.cadastrar(dados);
    }
}
