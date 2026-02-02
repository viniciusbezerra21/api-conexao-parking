package conexao_parking.api.domain.veiculo;

import conexao_parking.api.domain.proprietario.Proprietario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
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
    @Column(name = "id_veiculo")
    private Long idVeiculo;

    @ManyToOne
    @JoinColumn(name = "id_proprietario", nullable = false)
    private Proprietario proprietario;

    private String numeroPlaca;
    private String cor;
    private Boolean visitante;

    @Enumerated(EnumType.STRING)
    private StatusVeiculo status;
    @Enumerated(EnumType.STRING)
    private TipoVeiculo tipoVeiculo;

    private Boolean excluido;

    @Column(nullable = false)
    private Boolean bloqueado = false;

    public Veiculo(DadosCadastroVeiculo dados, Proprietario proprietario) {
        this.cor = dados.cor();
        this.numeroPlaca = dados.numeroPlaca();
        this.visitante = dados.visitante();
        this.tipoVeiculo = dados.tipoVeiculo();
        this.proprietario =  proprietario;
        this.status = dados.status();
        this.excluido = false;
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoVeiculo dados) {
        if (dados.numeroPlaca() != null) {
           this.numeroPlaca = dados.numeroPlaca();
        }
        if (dados.tipoVeiculo() != null) {
            this.tipoVeiculo = dados.tipoVeiculo();
        }
        if (dados.status() != null) {
            this.status = dados.status();
        }
        if (dados.cor() != null) {
            this.cor = dados.cor();
        }
    }

    public void excluir() {
        this.excluido = true;
    }

    public void bloquear() {
        this.bloqueado = true;
    }

    public void desbloquear() {
        this.bloqueado = false;
    }
}
