package conexao_parking.api.domain.usuario;

public record DadosRedefinicaoSenha(
        String novaSenha,
        String confirmacaoSenha
) {
    public String getNovaSenha() {
        return novaSenha;
    }

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

}
