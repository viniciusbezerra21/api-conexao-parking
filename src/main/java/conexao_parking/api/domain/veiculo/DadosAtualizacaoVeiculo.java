package conexao_parking.api.domain.veiculo;

import conexao_parking.api.domain.proprietario.DadosAtualizacaoProprietario;
import conexao_parking.api.domain.proprietario.Proprietario;

public record DadosAtualizacaoVeiculo(
      Long id_veiculo,
      String numero_placa,
      TipoVeiculo tipo_veiculo,
      StatusVeiculo status,
      String cor
) {
      public DadosAtualizacaoVeiculo (Veiculo veiculo) {
            this(
                    veiculo.getIdVeiculo(),
                    veiculo.getNumero_placa(),
                    veiculo.getTipo_veiculo(),
                    veiculo.getStatus(),
                    veiculo.getCor()
            );
      }
}
