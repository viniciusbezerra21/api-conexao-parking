package conexao_parking.api.domain.proprietario;

public record DadosListagemProprietario(
        String nome,
        String cpf_proprietario
) {
    public DadosListagemProprietario(Proprietario proprietario) {
        this(
                proprietario.getNome(),
                proprietario.getCpf_proprietario()
        );
    }
}
