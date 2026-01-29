package conexao_parking.api.domain.usuario.validador;

public interface ValidadorUsuario<T> {
    void validar(T dados);
}
