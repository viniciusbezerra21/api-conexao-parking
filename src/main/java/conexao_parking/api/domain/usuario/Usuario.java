package conexao_parking.api.domain.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@EqualsAndHashCode(of = "idUsuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private long idUsuario;

    @Column(name = "email_corporativo")
    private String emailCorporativo;

    @Column(name = "precisa_trocar_senha")
    private boolean precisaTrocarSenha;

    @JsonIgnore
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
        this.precisaTrocarSenha = true;
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

    public void tornarUsuario() {
        this.role = Role.ROLE_USER;
    }

    public void excluir() {
        this.ativo = false;
    }
}

