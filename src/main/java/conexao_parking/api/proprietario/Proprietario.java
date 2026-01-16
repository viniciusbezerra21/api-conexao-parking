package conexao_parking.api.proprietario;


import jakarta.persistence.*;
import jakarta.validation.Valid;
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

    public void atualizarInformacoes(@Valid DadosAtualizacaoProprietario dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.cpf_proprietario() != null) {
            this.cpf_proprietario = dados.cpf_proprietario();
        }
    }
}
