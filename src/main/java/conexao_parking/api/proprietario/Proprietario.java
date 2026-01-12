package conexao_parking.api.proprietario;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "proprietario")
@Entity(name = "Proprietario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_proprietario")
public class Proprietario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_proprietario;
    private String nome;
    private String cpf_proprietario;

    public Proprietario(DadosCadastroProprietario dados) {
        this.nome = dados.nome();
        this.cpf_proprietario = dados.cpf_proprietario();
    }
}
