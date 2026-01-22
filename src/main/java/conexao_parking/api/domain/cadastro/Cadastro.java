package conexao_parking.api.domain.cadastro;

import conexao_parking.api.domain.usuario.Usuario;
import conexao_parking.api.domain.veiculo.Veiculo;
import jakarta.persistence.*;


@Entity
@Table(name = "cadastro")
public class Cadastro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_veiculo")
    private Veiculo veiculo;

    protected Cadastro() {}

    public Cadastro(Usuario usuario, Veiculo veiculo) {
        this.usuario = usuario;
        this.veiculo = veiculo;
    }

}
