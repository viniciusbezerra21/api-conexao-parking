package conexao_parking.api.controller;

import conexao_parking.api.usuario.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroUsuario usuario) {
    repository.save(new Usuario(usuario));
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoUsuario dados) {
        var usuario = repository.getReferenceById(dados.id_usuario());
        usuario.atualizarInformacoes(dados);
    }

    @DeleteMapping
    @Transactional
    public void deletar(@RequestBody @Valid DadosExclusaoUsuario dados) {
        var usuario = repository.getReferenceById(dados.id_usuario());
        usuario.deletar(dados);
    }
}
