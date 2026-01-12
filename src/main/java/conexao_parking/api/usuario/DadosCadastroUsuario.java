package conexao_parking.api.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(
        @NotBlank
        @Email
        String email_corporativo,
        @NotBlank
        String senha) {

}
