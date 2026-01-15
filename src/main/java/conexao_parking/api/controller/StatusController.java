package conexao_parking.api.controller;


import conexao_parking.api.status.DadaosCadastroStatus;
import conexao_parking.api.status.Status;
import conexao_parking.api.status.StatusRepository;
import conexao_parking.api.status.StatusService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("status")
public class StatusController {
    private final StatusService service;

    @Autowired
    private StatusRepository repository;

    public StatusController(StatusService service) {
        this.service = service;
    }

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadaosCadastroStatus dados) {
        service.cadastrar(dados);
    }
}
