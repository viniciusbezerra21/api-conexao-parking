package conexao_parking.api.domain.usuario;

public record DadosCadastroResponse(
        DadosDetalhamentoUsuario usuario,
        String token
) {
}
