package conexao_parking.api.domain.proprietario;


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
    @Column(name = "id_proprietario")
    private long idProprietario;
    private String nome;
    @Column(name = "cpf_proprietario")
    private String cpfProprietario;

    public Proprietario(DadosCadastroProprietario dados) {
        this.nome = dados.nome();
        this.cpfProprietario = dados.cpfProprietario();
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoProprietario dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.cpfProprietario() != null) {
            this.cpfProprietario = dados.cpfProprietario();
        }
    }
}
