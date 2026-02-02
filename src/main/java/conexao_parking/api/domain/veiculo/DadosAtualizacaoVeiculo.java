package conexao_parking.api.domain.veiculo;

import conexao_parking.api.domain.proprietario.DadosAtualizacaoProprietario;
import conexao_parking.api.domain.proprietario.Proprietario;

public record DadosAtualizacaoVeiculo(
      Long idVeiculo,
      String numeroPlaca,
      TipoVeiculo tipoVeiculo,
      StatusVeiculo status,
      String cor
) {
      public DadosAtualizacaoVeiculo (Veiculo veiculo) {
            this(
                    veiculo.getIdVeiculo(),
                    veiculo.getNumeroPlaca(),
                    veiculo.getTipoVeiculo(),
                    veiculo.getStatus(),
                    veiculo.getCor()
            );
      }
}
