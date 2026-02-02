package conexao_parking.api.domain.veiculo;

import conexao_parking.api.domain.proprietario.Proprietario;

public record DadosDetalhamentoVeiculo (
        Long idVeiculo,
        String numeroPlaca,
        String cor,
        Boolean visitante,
        TipoVeiculo tipoVeiculo,
        StatusVeiculo status,
        Proprietario proprietario,
        Boolean bloqueado
) {
    public DadosDetalhamentoVeiculo(Veiculo veiculo) {
        this(
                veiculo.getIdVeiculo(),
                veiculo.getNumeroPlaca(),
                veiculo.getCor(),
                veiculo.getVisitante(),
                veiculo.getTipoVeiculo(),
                veiculo.getStatus(),
                veiculo.getProprietario(),
                veiculo.getBloqueado()
        );
    }
}
