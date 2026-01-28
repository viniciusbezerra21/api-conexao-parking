package conexao_parking.api.domain.movimentacao;

import java.time.LocalDateTime;

public record DadosListagemMovimentacao(
        String numero_placa,
        LocalDateTime data_entrada,
        LocalDateTime data_saida,
        String observacaoEntrada,
        String observacaoSaida
) {
    public DadosListagemMovimentacao(Movimentacao movimentacao) {
        this(
                movimentacao.getVeiculo().getNumero_placa(),
                movimentacao.getDataEntrada(),
                movimentacao.getDataSaida(),
                movimentacao.getObservacaoEntrada(),
                movimentacao.getObservacaoSaida()
        );
    }

}
