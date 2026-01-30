package conexao_parking.api.domain.veiculo;

import conexao_parking.api.domain.proprietario.Proprietario;

public record DadosListagemVeiculoDashboard(
        Proprietario proprietario,
        TipoVeiculo tipo_veiculo,
        String numero_placa,
        String cor,
        StatusVeiculo status,
        Boolean bloqueado

) {
    public DadosListagemVeiculoDashboard(Veiculo veiculo) {
        this(
                veiculo.getProprietario(),
                veiculo.getTipo_veiculo(),
                veiculo.getNumero_placa(),
                veiculo.getCor(),
                veiculo.getStatus(),
                veiculo.getBloqueado()
        );
    }
}
