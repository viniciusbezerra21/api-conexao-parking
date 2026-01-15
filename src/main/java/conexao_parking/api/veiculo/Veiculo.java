package conexao_parking.api.veiculo;

import conexao_parking.api.proprietario.Proprietario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "veiculo")
@Entity(name = "Veiculo")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_veiculo")
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_veiculo;

    @ManyToOne
    @JoinColumn(name = "id_proprietario", nullable = false)
    private Proprietario proprietario;

    private String numero_placa;
    private String cor;
    private Boolean visitante;

    @Enumerated(EnumType.STRING)
    private StatusVeiculo status;
    @Enumerated(EnumType.STRING)
    private TipoVeiculo tipo_veiculo;

    public Veiculo(DadosCadastroVeiculo dados, Proprietario proprietario) {
        this.cor = dados.cor();
        this.numero_placa = dados.numero_placa();
        this.visitante = dados.visitante();
        this.tipo_veiculo = dados.tipo_veiculo();
        this.proprietario =  proprietario;
        this.status = dados.status();
    }
}
