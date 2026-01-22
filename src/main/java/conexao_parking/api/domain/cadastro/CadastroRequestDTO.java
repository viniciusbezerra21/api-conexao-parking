package conexao_parking.api.domain.cadastro;


import jakarta.validation.constraints.NotNull;

public record CadastroRequestDTO(
        @NotNull
        long id_usuario,
        @NotNull
        long id_veiculo
) {
}
