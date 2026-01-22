package conexao_parking.api.domain.movimentacao;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosCadastroMovimentacao(
        @NotNull
        long id_veiculo,
        @NotNull
        LocalDateTime data_entrada,
        @NotNull
        LocalDateTime data_saida
) {
}
