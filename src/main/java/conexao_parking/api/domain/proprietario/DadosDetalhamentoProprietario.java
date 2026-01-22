package conexao_parking.api.domain.proprietario;


public record DadosDetalhamentoProprietario(
        long id_proprietario,
        String nome,
        String cpf_proprietario
) {
    public DadosDetalhamentoProprietario(Proprietario proprietario) {
        this(
                proprietario.getId_proprietario(),
                proprietario.getNome(),
                proprietario.getCpf_proprietario()
        );
    }
}
