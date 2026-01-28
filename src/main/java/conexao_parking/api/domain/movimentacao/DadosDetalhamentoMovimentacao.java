package conexao_parking.api.domain.movimentacao;


import java.time.LocalDateTime;

public record DadosDetalhamentoMovimentacao(
        long idMovimentacao,
        LocalDateTime data_entrada,
        LocalDateTime data_saida,
        String observacaoEntrada,
        String observacaoSaida
) {
    public DadosDetalhamentoMovimentacao(Movimentacao movimentacao) {
        this(
                movimentacao.getId(),
                movimentacao.getDataEntrada(),
                movimentacao.getDataSaida(),
                movimentacao.getObservacaoEntrada(),
                movimentacao.getObservacaoSaida()
        );
    }
}
