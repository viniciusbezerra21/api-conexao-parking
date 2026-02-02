package conexao_parking.api.domain.veiculo;

import conexao_parking.api.domain.proprietario.DadosCadastroProprietario;
import jakarta.persistence.Embedded;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroVeiculo(
        @NotBlank
        String numeroPlaca,
        @NotBlank
        String cor,
        @NotNull
        Boolean visitante,
        @Enumerated
        TipoVeiculo tipoVeiculo,
        @Enumerated
        StatusVeiculo status,
        @NotNull
        @Embedded
        DadosCadastroProprietario proprietario

) {
}
