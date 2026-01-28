package conexao_parking.api.domain.cadastro;

import conexao_parking.api.domain.usuario.Usuario;
import conexao_parking.api.domain.veiculo.Veiculo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.time.LocalDateTime;


@Table(name = "cadastro")
@Entity(name = "Cadastro")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cadastro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_veiculo")
    private Veiculo veiculo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    public Cadastro(Usuario usuario, Veiculo veiculo, LocalDateTime dataCadastro) {
        this.veiculo = veiculo;
        this.usuario = usuario;
        this.dataCadastro = dataCadastro;
    }
}
