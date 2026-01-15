package conexao_parking.api.movimentacao;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosListagemMovimentacao(
        @NotNull
        LocalDateTime data_entrada,
        @NotNull
        LocalDateTime data_saida
) {
    public DadosListagemMovimentacao(Movimentacao movimentacao) {
        this(
                movimentacao.getData_entrada(),
                movimentacao.getData_saida()
        );
    }
}
