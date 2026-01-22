package conexao_parking.api.controller;

import conexao_parking.api.domain.cadastro.CadastroRepository;
import conexao_parking.api.domain.cadastro.CadastroRequestDTO;
import conexao_parking.api.domain.cadastro.CadastroService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cadastro")
public class CadastroController {

    private CadastroRepository repository;

    private final CadastroService cadastroService;

    public CadastroController(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid CadastroRequestDTO dto) {
        cadastroService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
