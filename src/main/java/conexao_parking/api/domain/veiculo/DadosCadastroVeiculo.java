package conexao_parking.api.domain.veiculo;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroVeiculo(
        @NotNull
        long id_proprietario,
        @NotBlank
        String numero_placa,
        @NotBlank
        String cor,
        @NotNull
        Boolean visitante,
        @Enumerated
        TipoVeiculo tipo_veiculo,
        @Enumerated
        StatusVeiculo status
) {
}
