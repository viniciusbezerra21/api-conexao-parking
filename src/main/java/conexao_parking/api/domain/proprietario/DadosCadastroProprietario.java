package conexao_parking.api.domain.proprietario;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record DadosCadastroProprietario(
        @NotBlank
        String nome,
        @NotBlank
        @CPF(message = "CPF inválido")
        String cpfProprietario
) {
}
