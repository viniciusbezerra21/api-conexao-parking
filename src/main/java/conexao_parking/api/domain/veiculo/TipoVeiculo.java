package conexao_parking.api.domain.veiculo;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TipoVeiculo {
    @JsonProperty("Carro")
    CARRO,
    @JsonProperty("Moto")
    MOTO,
    @JsonProperty("Caminhão")
    CAMINHAO,
}
