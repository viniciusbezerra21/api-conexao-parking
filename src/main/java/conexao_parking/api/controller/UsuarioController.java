package conexao_parking.api.controller;

import conexao_parking.api.domain.usuario.*;
import conexao_parking.api.infra.security.DadosTokenJWT;
import conexao_parking.api.infra.security.SecurityConfigurations;
import conexao_parking.api.infra.security.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("usuario")
public class UsuarioController {


    @Autowired
    private UsuarioRepository repository;

    private final UsuarioService service;

    @Autowired
    private TokenService tokenService;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder) {
        var usuario = service.cadastrar(dados);

        var uri = uriBuilder.path("/usuario/{id}")
                .buildAndExpand(usuario.getId_usuario())
                .toUri();

        var tokenJWT = tokenService.gerarToken(usuario);

        return ResponseEntity.created(uri).body(new DadosCadastroResponse
                (new DadosDetalhamentoUsuario(usuario), tokenJWT));
    }



    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<DadosListagemUsuario>> listar(@PageableDefault(size = 10)Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemUsuario::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<DadosDetalhamentoUsuario> detalhar(@PathVariable Long id) {
        var usuario = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity atualizar(@PathVariable Long id,@RequestBody @Valid DadosAtualizacaoUsuario dados, @AuthenticationPrincipal Usuario usuarioLogado) {
        var usuarioAtualizado = service.atualizar(id, dados, usuarioLogado);
        return ResponseEntity.ok(new DadosAtualizacaoUsuario(usuarioAtualizado.getId_usuario(), usuarioAtualizado.getEmailCorporativo(), null));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping({"/{id}"})
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity excluir(@PathVariable Long id) {
        var usuario = repository.getReferenceById(id);
        usuario.excluir();

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/usuario/{id}/tornar-admin")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity tornarAdmin(@PathVariable Long id) {
        var usuario = repository.getReferenceById(id);
        usuario.tornarAdmin();
        return ResponseEntity.noContent().build();
    }

}
