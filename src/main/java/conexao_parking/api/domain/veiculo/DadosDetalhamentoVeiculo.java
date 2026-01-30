package conexao_parking.api.domain.veiculo;

import conexao_parking.api.domain.proprietario.Proprietario;

public record DadosDetalhamentoVeiculo (
        Long idVeiculo,
        String numero_placa,
        String cor,
        Boolean visitante,
        TipoVeiculo tipo_veiculo,
        StatusVeiculo status,
        Proprietario proprietario,
        Boolean bloqueado
) {
    public DadosDetalhamentoVeiculo(Veiculo veiculo) {
        this(
                veiculo.getIdVeiculo(),
                veiculo.getNumero_placa(),
                veiculo.getCor(),
                veiculo.getVisitante(),
                veiculo.getTipo_veiculo(),
                veiculo.getStatus(),
                veiculo.getProprietario(),
                veiculo.getBloqueado()
        );
    }
}
