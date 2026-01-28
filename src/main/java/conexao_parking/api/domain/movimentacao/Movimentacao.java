package conexao_parking.api.domain.movimentacao;


import conexao_parking.api.domain.veiculo.Veiculo;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Table(name = "movimentacao")
@Entity(name = "Movimentacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimentacao")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_veiculo", nullable = false)
    private Veiculo veiculo;

    @Column(name = "data_entrada")
    private LocalDateTime dataEntrada;
    @Column(name = "data_saida")
    private LocalDateTime dataSaida;

    @Column(name = "observacao_entrada")
    private String observacaoEntrada;

    @Column(name = "observacao_saida")
    private String observacaoSaida;

    public Movimentacao(Veiculo veiculo, LocalDateTime dataEntrada, LocalDateTime dataSaida, String observacaoEntrada, String observacaoSaida) {
        this.veiculo = veiculo;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.observacaoEntrada = observacaoEntrada;
        this.observacaoSaida = observacaoSaida;
    }
}
