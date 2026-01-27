package conexao_parking.api.domain.usuario;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuario")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_usuario;

    @Column(name = "email_corporativo")
    private String emailCorporativo;

    private String senha;
    private Boolean ativo;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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

    @Override
    public boolean isEnabled() {
        return ativo;
    }

    public Usuario(DadosCadastroUsuario dados,  String senhaHash) {
        this.emailCorporativo = dados.emailCorporativo();
        this.senha = senhaHash;
        this.ativo = true;
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoUsuario dados) {
        if (dados.emailCorporativo() != null) {
            this.emailCorporativo = dados.emailCorporativo();
        }
        if (dados.senha() != null) {
            this.senha = dados.senha();
        }
    }


    public void excluir() {
        this.ativo = false;
    }
}

