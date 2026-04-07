package conexao_parking.api.domain.usuario;

import conexao_parking.api.domain.usuario.validador.ValidadorUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Usuario cadastrar(DadosCadastroUsuario dados) {
        String senhaPadrao = "Conexao@123";
        var senhaHash = passwordEncoder.encode(senhaPadrao);
        var usuario = new Usuario(dados, senhaHash, Role.ROLE_USER);
        usuario.setPrecisaTrocarSenha(true);

        return repository.save(usuario);
    }

    public Usuario atualizar(Long idUsuario, DadosAtualizacaoUsuario dados, Usuario usuarioLogado) {
        var usuario = repository.getReferenceById(idUsuario);

        if (!usuario.equals(usuarioLogado) && usuarioLogado.getRole() != Role.ROLE_ADMIN) {
            throw new RuntimeException("Acesso negado: apenas o próprio usuário ou um administrador pode atualizar os dados.");
        }

        if (dados.emailCorporativo() != null) {
            usuario.setEmailCorporativo(dados.emailCorporativo());
        }

        if (dados.novaSenha() != null && !dados.novaSenha().isBlank()) {
            validadoresSenha.forEach(v -> v.validar(dados.novaSenha()));
            usuario.setSenha(passwordEncoder.encode(dados.novaSenha()));

            usuario.setPrecisaTrocarSenha(false);
        }

        return usuario;
    }

    public boolean emailDisponivel(String emailCorporativo) {
        return !repository.existsByEmailCorporativo(emailCorporativo);
    }
}
