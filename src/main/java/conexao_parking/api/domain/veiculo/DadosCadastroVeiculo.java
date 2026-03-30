package conexao_parking.api.domain.veiculo;

import conexao_parking.api.domain.proprietario.DadosCadastroProprietario;
import jakarta.persistence.Embedded;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroVeiculo(
        @NotBlank
        @Pattern(
                regexp = "^[A-Z]{3}-?[0-9][A-Z0-9][0-9]{2}$",
                message = "A placa deve seguir o padrão Mercosul (ABC1D23) ou antigo (ABC1234)"
        )
        String numeroPlaca,

        @NotBlank
        @Pattern(regexp = "^[A-Za-z]+$", message = "A cor deve conter apenas letras")
        String cor,

        @NotNull
        Boolean visitante,

        @NotNull
        TipoVeiculo tipoVeiculo,

        @NotNull
        StatusVeiculo status,

        @NotNull
        @Embedded
        @Valid
        DadosCadastroProprietario proprietario,

        @NotBlank
        @Pattern(regexp = "^[A-Za-zÀ-ÿ0-9\\s.,&'-]+$", message = "O nome da empresa deve conter apenas letras")
        String empresa
) {
}
