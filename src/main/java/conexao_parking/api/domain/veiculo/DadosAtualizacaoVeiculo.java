package conexao_parking.api.domain.veiculo;



public record DadosAtualizacaoVeiculo(
      Long idVeiculo,
      String numeroPlaca,
      TipoVeiculo tipoVeiculo,
      StatusVeiculo status,
      String cor,
      String empresa
) {
      public DadosAtualizacaoVeiculo (Veiculo veiculo) {
            this(
                    veiculo.getIdVeiculo(),
                    veiculo.getNumeroPlaca(),
                    veiculo.getTipoVeiculo(),
                    veiculo.getStatus(),
                    veiculo.getCor(),
                    veiculo.getEmpresa()
            );
      }
}
