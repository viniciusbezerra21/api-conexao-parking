package conexao_parking.api.domain.observacao;


public record DadosDetalhamentoObservacao(
        long id,
        String observacao_saida,
        String observacao_entrada
) {
    public DadosDetalhamentoObservacao(Observacao observacao) {
        this(
                observacao.getId(),
                observacao.getObservacao_saida(),
                observacao.getObservacao_entrada()
        );
    }
}
