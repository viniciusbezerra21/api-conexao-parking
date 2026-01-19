package conexao_parking.api.observacao;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Observacao")
@Table(name = "observacoes")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Observacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String observacao_entrada;
    private String observacao_saida;

    public Observacao (DadosCadastroObservacao dados) {
       this.observacao_entrada = dados.observacao_entrada();
       this.observacao_saida = dados.observacao_saida();
    }

}

