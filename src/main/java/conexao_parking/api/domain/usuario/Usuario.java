package conexao_parking.api.domain.usuario;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuario")
@Entity(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private long idUsuario;

    @Column(name = "email_corporativo")
    private String emailCorporativo;

    private String senha;
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(ativo);
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return emailCorporativo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public Usuario(DadosCadastroUsuario dados,  String senhaHash, Role role) {
        this.emailCorporativo = dados.emailCorporativo();
        this.senha = senhaHash;
        this.ativo = true;
        this.role = role;
    }



    public Usuario(String emailCorporativo, String senhaHash, Role role) {
        this.emailCorporativo = emailCorporativo;
        this.senha = senhaHash;
        this.role = role;
        this.ativo = true;
    }

    public void tornarAdmin() {
        this.role = Role.ROLE_ADMIN;
    }


    public void excluir() {
        this.ativo = false;
    }
}

