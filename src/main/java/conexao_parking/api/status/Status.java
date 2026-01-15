package conexao_parking.api.status;


import conexao_parking.api.veiculo.Veiculo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "status")
@Entity(name = "Status")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Status {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_veiculo", nullable = false)
    private Veiculo veiculo;

    private boolean ativo;
    private boolean inativo;
    private boolean bloqueado;

    public Status(DadaosCadastroStatus dados, Veiculo veiculo) {
        this.ativo = dados.ativo();
        this.inativo = dados.inativo();
        this.bloqueado = dados.bloqueado();
        this.veiculo = veiculo;

    }
}
