package conexao_parking.api.domain.movimentacao.validadores;

import conexao_parking.api.domain.ValidacaoException;
import conexao_parking.api.domain.movimentacao.DadosMovimentacaoEntrada;
import conexao_parking.api.domain.veiculo.VeiculoRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidadorVeiculoBloqueado implements ValidadorMovimentacao<DadosMovimentacaoEntrada> {

    private final VeiculoRepository veiculoRepository;

    public ValidadorVeiculoBloqueado(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @Override
    public void validar(DadosMovimentacaoEntrada dados) {
        boolean veiculoBloqueado = veiculoRepository.existsByIdVeiculoAndBloqueadoTrue(dados.idVeiculo());
        if (veiculoBloqueado) {
            throw new ValidacaoException("Veiculo bloqueado!");
        }
    }

}
