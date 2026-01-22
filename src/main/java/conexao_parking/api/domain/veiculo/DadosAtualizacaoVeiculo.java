package conexao_parking.api.domain.veiculo;

public record DadosAtualizacaoVeiculo(
      long id_veiculo,
      String numero_placa,
      TipoVeiculo tipo_veiculo,
      StatusVeiculo status,
      String cor
) {
      public DadosAtualizacaoVeiculo (Veiculo veiculo) {
            this(
                    veiculo.getId_veiculo(),
                    veiculo.getNumero_placa(),
                    veiculo.getTipo_veiculo(),
                    veiculo.getStatus(),
                    veiculo.getCor()
            );
      }
}
