package conexao_parking.api.domain.usuario;

import conexao_parking.api.domain.usuario.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(
        long id_usuario,
        String emailCorporativo,
        String novaSenha
) {
}
