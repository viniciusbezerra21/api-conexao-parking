package conexao_parking.api.controller;

import conexao_parking.api.usuario.DadosCadastroUsuario;
import conexao_parking.api.usuario.Usuario;
import conexao_parking.api.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroUsuario usuario) {
    repository.save(new Usuario(usuario));
    }
}
