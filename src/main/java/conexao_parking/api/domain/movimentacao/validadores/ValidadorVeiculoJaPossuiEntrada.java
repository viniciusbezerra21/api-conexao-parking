package conexao_parking.api.domain.movimentacao.validadores;

import conexao_parking.api.domain.ValidacaoException;
import conexao_parking.api.domain.movimentacao.DadosMovimentacaoEntrada;
import conexao_parking.api.domain.movimentacao.MovimentacaoRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidadorVeiculoJaPossuiEntrada implements ValidadorMovimentacao<DadosMovimentacaoEntrada> {

    private final MovimentacaoRepository movimentacaoRepository;

    public ValidadorVeiculoJaPossuiEntrada(MovimentacaoRepository movimentacaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @Override
    public void validar(DadosMovimentacaoEntrada dados) {
        boolean veiculoJaEntrou = movimentacaoRepository.existsByVeiculoIdVeiculoAndDataSaidaIsNull(dados.idVeiculo());

        if  (veiculoJaEntrou) {
            throw new ValidacaoException("Veiculo ja possui entrada ativa");
        }
    }
}
