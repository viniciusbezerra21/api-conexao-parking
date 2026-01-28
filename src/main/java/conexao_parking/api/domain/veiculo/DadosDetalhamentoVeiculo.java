package conexao_parking.api.domain.veiculo;

public record DadosDetalhamentoVeiculo (
        Long idVeiculo,
        String numero_placa,
        String cor,
        Boolean visitante,
        TipoVeiculo tipo_veiculo,
        StatusVeiculo status,
        String nome
) {
    public DadosDetalhamentoVeiculo(Veiculo veiculo) {
        this(
                veiculo.getIdVeiculo(),
                veiculo.getNumero_placa(),
                veiculo.getCor(),
                veiculo.getVisitante(),
                veiculo.getTipo_veiculo(),
                veiculo.getStatus(),
                veiculo.getProprietario().getNome()
        );
    }
}
