package conexao_parking.api.cadastro;


public record CadastroRequestDTO(
        long id_usuario,
        long id_veiculo
) {
}
