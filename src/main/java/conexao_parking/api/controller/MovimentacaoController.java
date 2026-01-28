package conexao_parking.api.controller;

import conexao_parking.api.domain.movimentacao.*;
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
@RequestMapping("movimentacao")
public class MovimentacaoController {



    @Autowired
    private MovimentacaoRepository repository;





    @GetMapping
    public ResponseEntity<Page<DadosListagemMovimentacao>> listar(@PageableDefault(size = 10)Pageable paginacao) {
        var page = repository.findAll(paginacao).map(DadosListagemMovimentacao::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMovimentacao> detalhar(@PathVariable Long id) {
        var movimentacao = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMovimentacao(movimentacao));
    }


}
