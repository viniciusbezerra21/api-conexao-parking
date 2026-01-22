package conexao_parking.api.domain.movimentacao;

import java.time.LocalDateTime;

public record DadosDetalhamentoMovimentacao(
        long id_movimentacao,
        long id_veiculo,
        LocalDateTime data_entrada,
        LocalDateTime data_saida

) {
    public DadosDetalhamentoMovimentacao(Movimentacao movimentacao) {
        this(
          movimentacao.getId_movimentacao(),
          movimentacao.getVeiculo().getId_veiculo(),
          movimentacao.getData_entrada(),
          movimentacao.getData_saida()
        );
    }

}
