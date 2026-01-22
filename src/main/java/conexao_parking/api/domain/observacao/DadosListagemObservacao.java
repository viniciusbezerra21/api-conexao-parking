package conexao_parking.api.domain.observacao;

public record DadosListagemObservacao(
        long id,
        String observacao_entrada,
        String observacao_saida
) {
    public DadosListagemObservacao(Observacao observacao) {
        this(
                observacao.getId(),
                observacao.getObservacao_entrada(),
                observacao.getObservacao_saida()
        );
    }
}
