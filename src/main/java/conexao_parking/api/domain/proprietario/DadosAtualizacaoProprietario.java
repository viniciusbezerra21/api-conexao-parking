package conexao_parking.api.domain.proprietario;

public record DadosAtualizacaoProprietario(
        long id_proprietario,
        String nome,
        String cpf_proprietario
) {
    public DadosAtualizacaoProprietario (Proprietario proprietario) {
        this(
                proprietario.getId_proprietario(),
                proprietario.getNome(),
                proprietario.getCpf_proprietario()
        );
    }
}
