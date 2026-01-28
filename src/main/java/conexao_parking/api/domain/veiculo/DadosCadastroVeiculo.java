package conexao_parking.api.domain.veiculo;

import conexao_parking.api.domain.observacao.DadosCadastroObservacao;
import conexao_parking.api.domain.proprietario.DadosCadastroProprietario;
import conexao_parking.api.domain.usuario.DadosDetalhamentoUsuario;
import jakarta.persistence.Embedded;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroVeiculo(
        @NotBlank
        String numero_placa,
        @NotBlank
        String cor,
        @NotNull
        Boolean visitante,
        @Enumerated
        TipoVeiculo tipo_veiculo,
        @Enumerated
        StatusVeiculo status,
        @NotNull
        @Embedded
        DadosCadastroProprietario proprietario

) {
}
