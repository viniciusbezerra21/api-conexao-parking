package conexao_parking.api.domain.veiculo;

import conexao_parking.api.domain.proprietario.Proprietario;

public record DadosDetalhamentoVeiculo(
        Long idVeiculo,
        String numeroPlaca,
        String cor,
        String empresa,
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
                veiculo.getEmpresa(),
                veiculo.getVisitante(),
                veiculo.getTipoVeiculo(),
                veiculo.getStatus(),
                veiculo.getProprietario(),
                veiculo.getBloqueado()
        );
    }
}
