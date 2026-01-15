package conexao_parking.api.veiculo;

public record DadosListagemVeiculosCadastrados(
        String nome,
        String cpf,
        TipoVeiculo tipo_veiculo,
        String numero_placa,
        String cor
) {
    public DadosListagemVeiculosCadastrados(Veiculo veiculo) {
        this(
             veiculo.getProprietario().getNome(),
             veiculo.getProprietario().getCpf_proprietario(),
             veiculo.getTipo_veiculo(),
             veiculo.getNumero_placa(),
             veiculo.getCor()
        );
    }
}
