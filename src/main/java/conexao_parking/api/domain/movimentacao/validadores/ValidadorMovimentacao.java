package conexao_parking.api.domain.movimentacao.validadores;

public interface ValidadorMovimentacao<T> {
    void validar(T dados);
}
