package conexao_parking.api.domain.movimentacao;

import java.time.LocalDateTime;

public record DadosListagemMovimentacao(
        String numeroPlaca,
        LocalDateTime dataEntrada,
        LocalDateTime dataSaida,
        String observacaoEntrada,
        String observacaoSaida
) {
    public DadosListagemMovimentacao(Movimentacao movimentacao) {
        this(
                movimentacao.getVeiculo().getNumeroPlaca(),
                movimentacao.getDataEntrada(),
                movimentacao.getDataSaida(),
                movimentacao.getObservacaoEntrada(),
                movimentacao.getObservacaoSaida()
        );
    }

}
