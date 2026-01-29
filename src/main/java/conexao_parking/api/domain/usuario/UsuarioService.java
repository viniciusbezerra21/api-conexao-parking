package conexao_parking.api.domain.usuario;

import conexao_parking.api.domain.usuario.validador.ValidadorUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private List<ValidadorUsuario<String>> validadoresSenha;


    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrar(DadosCadastroUsuario dados) {
        validadoresSenha.forEach(v -> v.validar(dados.senha()));
        var senhaHash = passwordEncoder.encode(dados.senha());
        var usuario = new Usuario(dados, senhaHash,Role.ROLE_USER);
        return repository.save(usuario);
    }

    public Usuario atualizar(Long idUsuario, DadosAtualizacaoUsuario dados, Usuario usuarioLogado) {
        var usuario = repository.getReferenceById(idUsuario);

        if (usuarioLogado.getRole() == Role.ROLE_ADMIN) {
            if (dados.emailCorporativo() != null) {
                usuario.setEmailCorporativo(dados.emailCorporativo());
            }
            if (dados.novaSenha() != null && !dados.novaSenha().isBlank()) {
                validadoresSenha.forEach(v -> v.validar(dados.novaSenha()));
                usuario.setSenha(passwordEncoder.encode(dados.novaSenha()));
            }
        }

        return usuario;
    }
}
