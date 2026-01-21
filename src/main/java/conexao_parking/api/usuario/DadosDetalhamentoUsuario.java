package conexao_parking.api.usuario;

public record DadosDetalhamentoUsuario(
        long id_usuario,
        String email_corporativo,
        String senha,
        Boolean ativo
) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(
            usuario.getId_usuario(),
            usuario.getEmail_corporativo(),
            usuario.getSenha(),
            usuario.getAtivo()
        );
    }

}
