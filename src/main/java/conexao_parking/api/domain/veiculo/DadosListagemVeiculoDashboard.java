package conexao_parking.api.domain.veiculo;

import conexao_parking.api.domain.proprietario.Proprietario;

public record DadosListagemVeiculoDashboard(
        Proprietario proprietario,
        TipoVeiculo tipoVeiculo,
        String numeroPlaca,
        String cor,
        StatusVeiculo status,
        Boolean bloqueado

) {
    public DadosListagemVeiculoDashboard(Veiculo veiculo) {
        this(
                veiculo.getProprietario(),
                veiculo.getTipoVeiculo(),
                veiculo.getNumeroPlaca(),
                veiculo.getCor(),
                veiculo.getStatus(),
                veiculo.getBloqueado()
        );
    }
}
