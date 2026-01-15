package conexao_parking.api.status;

import jakarta.validation.constraints.NotNull;

public record DadaosCadastroStatus(
        @NotNull
        long id_veiculo,
        @NotNull
        boolean ativo,
        @NotNull
        boolean inativo,
        @NotNull
        boolean bloqueado
) {
}
