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
@EqualsAndHashCode(of = "idVeiculo")
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVeiculo;

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

    private Boolean excluido;

    @Column(nullable = false)
    private Boolean bloqueado = false;

    public Veiculo(DadosCadastroVeiculo dados, Proprietario proprietario) {
        this.cor = dados.cor();
        this.numero_placa = dados.numero_placa();
        this.visitante = dados.visitante();
        this.tipo_veiculo = dados.tipo_veiculo();
        this.proprietario =  proprietario;
        this.status = dados.status();
        this.excluido = false;
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoVeiculo dados) {
        if (dados.numero_placa() != null) {
           this.numero_placa = dados.numero_placa();
        }
        if (dados.tipo_veiculo() != null) {
            this.tipo_veiculo = dados.tipo_veiculo();
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
