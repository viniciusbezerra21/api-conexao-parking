package conexao_parking.api.infra.security;

import conexao_parking.api.domain.usuario.Role;
import conexao_parking.api.domain.usuario.Usuario;
import conexao_parking.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrap implements CommandLineRunner {

    @Autowired
    private UsuarioRepository repository;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            var senhaHash = passwordEncoder.encode(adminPassword);
            var admin = new Usuario(adminEmail, senhaHash, Role.ROLE_ADMIN);
            repository.save(admin);
        }
    }
}
