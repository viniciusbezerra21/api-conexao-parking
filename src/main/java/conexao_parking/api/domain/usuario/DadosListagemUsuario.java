package conexao_parking.api.domain.usuario;

public record DadosListagemUsuario(
        long id_usuario,
        String email_corporativo,
        String senha,
        Boolean ativo
) {
    public DadosListagemUsuario(Usuario usuario) {
        this(
                usuario.getId_usuario(),
                usuario.getEmail_corporativo(),
                usuario.getSenha(),
                usuario.getAtivo()
        );
    }
}
