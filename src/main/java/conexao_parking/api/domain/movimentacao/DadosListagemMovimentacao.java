package conexao_parking.api.domain.movimentacao;

import java.time.LocalDateTime;

public record DadosListagemMovimentacao(
        Long id,
        String numeroPlaca,
        LocalDateTime dataEntrada,
        LocalDateTime dataSaida,
        String observacaoEntrada,
        String observacaoSaida
) {
    public DadosListagemMovimentacao(Movimentacao movimentacao) {
        this(
                movimentacao.getId(),
                movimentacao.getVeiculo().getNumeroPlaca(),
                movimentacao.getDataEntrada(),
                movimentacao.getDataSaida(),
                movimentacao.getObservacaoEntrada(),
                movimentacao.getObservacaoSaida()
        );
    }

}
