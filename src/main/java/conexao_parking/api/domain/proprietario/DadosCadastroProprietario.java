package conexao_parking.api.domain.proprietario;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroProprietario(
        @NotBlank
        String nome,
        @NotBlank
        String cpf_proprietario
) {
}
