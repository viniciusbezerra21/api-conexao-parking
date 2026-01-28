package conexao_parking.api.domain.movimentacao;

import conexao_parking.api.domain.veiculo.Veiculo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Table(name = "movimentacao")
@Entity(name = "Movimentacao")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_movimentacao")
public class Movimentacao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_movimentacao;

    @ManyToOne
    @JoinColumn(name = "id_veiculo", nullable = false)
    private Veiculo veiculo;

    @Column(name = "data_entrada")
    private LocalDateTime data_entrada;
    @Column(name = "data_entrada")
    private LocalDateTime data_saida;

    @Column(name = "observacao_entrada")
    private String observacaoEntrada;

    @Column(name = "observacao_saida")
    private String observacaoSaida;

    public Movimentacao(Veiculo veiculo, LocalDateTime data_entrada, LocalDateTime data_saida, String observacaoEntrada, String observacaoSaida) {
        this.veiculo = veiculo;
        this.data_entrada = data_entrada;
        this.data_saida = data_saida;
        this.observacaoEntrada = observacaoEntrada;
        this.observacaoSaida = observacaoSaida;
    }
}
