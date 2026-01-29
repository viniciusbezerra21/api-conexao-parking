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
        validarSenha(dados.senha());
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
                validarSenha(dados.novaSenha());
                usuario.setSenha(passwordEncoder.encode(dados.novaSenha()));
            }
        }

        return usuario;
    }

    private void validarSenha(String senha) {
        if (senha.length() < 8) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres.");
        }
        if (!senha.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("A senha deve conter pelo menos uma letra maiúscula.");
        }
        if (!senha.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("A senha deve conter pelo menos uma letra minúscula.");
        }
        if (!senha.matches(".*\\d.*")) {
            throw new IllegalArgumentException("A senha deve conter pelo menos um número.");
        }
        if (!senha.matches(".*[!@#$%^&*()].*")) {
            throw new IllegalArgumentException("A senha deve conter pelo menos um caractere especial.");
        }
    }
}
