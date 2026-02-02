package conexao_parking.api.domain.usuario;

import conexao_parking.api.domain.usuario.Usuario;

public record DadosDetalhamentoUsuario(
        long idUsuario,
        String emailCorporativo,
        String senha,
        Boolean ativo
) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(
                usuario.getIdUsuario(),
                usuario.getEmailCorporativo(),
                usuario.getSenha(),
                usuario.getAtivo()
        );
    }

}
