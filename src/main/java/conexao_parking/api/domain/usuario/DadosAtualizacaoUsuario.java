package conexao_parking.api.domain.usuario;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(
        @NotNull
        Long idUsuario,
        @Email
        String emailCorporativo,

        String novaSenha
) {
}
