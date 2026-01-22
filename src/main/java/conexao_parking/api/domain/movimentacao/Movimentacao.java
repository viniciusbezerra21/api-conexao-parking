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

    private LocalDateTime data_entrada;
    private LocalDateTime data_saida;

    public Movimentacao(DadosCadastroMovimentacao dados, Veiculo veiculo) {
        this.data_entrada = dados.data_entrada();
        this.data_saida = dados.data_saida();
        this.veiculo = veiculo;
    }
}
