package conexao_parking.api.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "usuario")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_usuario")
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_usuario;
    private String email_corporativo;
    private String senha;

    public Usuario(DadosCadastroUsuario dados) {
        this.email_corporativo = dados.email_corporativo();
        this.senha = dados.senha();
    }
}

