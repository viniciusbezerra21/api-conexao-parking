package conexao_parking.api.domain.veiculo;

public record DadosListagemVeiculoDashboard(
        String nome,
        String cpf_proprietario,
        TipoVeiculo tipo_veiculo,
        String numero_placa,
        String cor,
        StatusVeiculo status,
        Boolean bloqueado

) {
    public DadosListagemVeiculoDashboard(Veiculo veiculo) {
        this(
                veiculo.getProprietario().getNome(),
                veiculo.getProprietario().getCpf_proprietario(),
                veiculo.getTipo_veiculo(),
                veiculo.getNumero_placa(),
                veiculo.getCor(),
                veiculo.getStatus(),
                veiculo.getBloqueado()
        );
    }
}
