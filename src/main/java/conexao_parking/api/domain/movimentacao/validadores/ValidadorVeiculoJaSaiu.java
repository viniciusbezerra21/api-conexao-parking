package conexao_parking.api.domain.movimentacao.validadores;

import conexao_parking.api.domain.ValidacaoException;
import conexao_parking.api.domain.movimentacao.DadosMovimentacaoSaida;
import conexao_parking.api.domain.movimentacao.MovimentacaoRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidadorVeiculoJaSaiu implements ValidadorMovimentacao<DadosMovimentacaoSaida> {

    private final MovimentacaoRepository movimentacaoRepository;

    public ValidadorVeiculoJaSaiu(MovimentacaoRepository movimentacaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @Override
    public void validar(DadosMovimentacaoSaida dados) {
        movimentacaoRepository.findByIdAndDataSaidaIsNull(dados.idMovimentacao())
                .orElseThrow(() -> new ValidacaoException("Veiculo já saiu"));
    }
}
