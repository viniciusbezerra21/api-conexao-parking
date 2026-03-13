package conexao_parking.api.domain.veiculo;

import conexao_parking.api.domain.proprietario.Proprietario;

public record DadosListagemVeiculoDashboard(
        Long idVeiculo,
        Proprietario proprietario,
        TipoVeiculo tipoVeiculo,
        String numeroPlaca,
        String cor,
        StatusVeiculo status,
        Boolean bloqueado

) {
    public DadosListagemVeiculoDashboard(Veiculo veiculo) {
        this(
                veiculo.getIdVeiculo(),
                veiculo.getProprietario(),
                veiculo.getTipoVeiculo(),
                veiculo.getNumeroPlaca(),
                veiculo.getCor(),
                veiculo.getStatus(),
                veiculo.getBloqueado()
        );
    }
}
