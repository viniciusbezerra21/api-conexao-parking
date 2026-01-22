package conexao_parking.api.domain.usuario;

public record DadosAtualizacaoUsuario(
        long id_usuario,
        String email_corporativo,
        String senha
) {
        public DadosAtualizacaoUsuario(Usuario usuario) {
                this(
                        usuario.getId_usuario(),
                        usuario.getEmail_corporativo(),
                        usuario.getSenha()
                );
        }
}
