package conexao_parking.api.domain.proprietario;

public record DadosAtualizacaoProprietario(
        long idProprietario,
        String nome,
        String cpfProprietario
) {
    public DadosAtualizacaoProprietario (Proprietario proprietario) {
        this(
                proprietario.getIdProprietario(),
                proprietario.getNome(),
                proprietario.getCpfProprietario()
        );
    }
}
