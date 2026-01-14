package conexao_parking.api.cadastro;

import conexao_parking.api.usuario.Usuario;
import conexao_parking.api.veiculo.Veiculo;
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
