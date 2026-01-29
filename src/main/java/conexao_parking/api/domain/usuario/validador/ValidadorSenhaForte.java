package conexao_parking.api.domain.usuario.validacao;

import conexao_parking.api.domain.ValidacaoException;
import conexao_parking.api.domain.usuario.validador.ValidadorUsuario;
import org.springframework.stereotype.Component;

@Component
public class ValidadorSenhaForte implements ValidadorUsuario<String> {

    @Override
    public void validar(String senha) {
        if (senha.length() < 8) {
            throw new ValidacaoException("A senha deve ter pelo menos 8 caracteres.");
        }
        if (!senha.matches(".*[A-Z].*")) {
            throw new ValidacaoException("A senha deve conter pelo menos uma letra maiúscula.");
        }
        if (!senha.matches(".*[a-z].*")) {
            throw new ValidacaoException("A senha deve conter pelo menos uma letra minúscula.");
        }
        if (!senha.matches(".*\\d.*")) {
            throw new ValidacaoException("A senha deve conter pelo menos um número.");
        }
        if (!senha.matches(".*[!@#$%^&*()].*")) {
            throw new ValidacaoException("A senha deve conter pelo menos um caractere especial.");
        }
    }
}