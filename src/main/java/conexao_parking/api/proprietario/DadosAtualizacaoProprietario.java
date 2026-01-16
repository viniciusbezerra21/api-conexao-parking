package conexao_parking.api.proprietario;

public record DadosAtualizacaoProprietario(
        long id_proprietario,
        String nome,
        String cpf_proprietario
) {
}
