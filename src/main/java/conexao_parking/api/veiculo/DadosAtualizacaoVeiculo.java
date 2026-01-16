package conexao_parking.api.veiculo;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoVeiculo(

      @NotNull
      long id_veiculo,
      String numero_placa,
      @Enumerated
      TipoVeiculo tipo_veiculo,
      @Enumerated
      StatusVeiculo status,
      String cor
) {
}
