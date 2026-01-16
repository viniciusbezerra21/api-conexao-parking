package conexao_parking.api.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(
        @NotNull
        long id_usuario,
        @Email
        String email_corporativo,
        String senha
) {
}
