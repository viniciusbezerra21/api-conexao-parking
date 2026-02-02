package conexao_parking.api.domain.proprietario;


public record DadosDetalhamentoProprietario(
        long idProprietario,
        String nome,
        String cpfProprietario
) {
    public DadosDetalhamentoProprietario(Proprietario proprietario) {
        this(
                proprietario.getIdProprietario(),
                proprietario.getNome(),
                proprietario.getCpfProprietario()
        );
    }
}
