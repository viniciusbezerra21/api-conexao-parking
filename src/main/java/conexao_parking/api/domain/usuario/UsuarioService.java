package conexao_parking.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrar(DadosCadastroUsuario dados) {
        var senhaHash = passwordEncoder.encode(dados.senha());
        var usuario = new Usuario(dados, senhaHash);
        return repository.save(usuario);
    }
}
