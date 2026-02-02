package conexao_parking.api.domain.cadastro;

import conexao_parking.api.domain.usuario.DadosCadastroUsuario;
import conexao_parking.api.domain.veiculo.DadosCadastroVeiculo;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosCadastroDTO(
        @NotNull
        DadosCadastroVeiculo veiculo,
        @NotNull
        DadosCadastroUsuario usuario,
        @NotNull
        LocalDateTime dataCadastro
) {
}
