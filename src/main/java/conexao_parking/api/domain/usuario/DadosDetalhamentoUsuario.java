package conexao_parking.api.domain.usuario;

import conexao_parking.api.domain.usuario.Usuario;

public record DadosDetalhamentoUsuario(
        long id_usuario,
        String emailCorporativo,
        String senha,
        Boolean ativo
) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(
                usuario.getId_usuario(),
                usuario.getEmailCorporativo(),
                usuario.getSenha(),
                usuario.getAtivo()
        );
    }

}
