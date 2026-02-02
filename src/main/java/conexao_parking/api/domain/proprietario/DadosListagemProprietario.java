package conexao_parking.api.domain.proprietario;

public record DadosListagemProprietario(
        String nome,
        String cpProprietario
) {
    public DadosListagemProprietario(Proprietario proprietario) {
        this(
                proprietario.getNome(),
                proprietario.getCpfProprietario()
        );
    }
}
