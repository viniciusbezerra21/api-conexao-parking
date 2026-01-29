package conexao_parking.api.domain.movimentacao;

import conexao_parking.api.domain.movimentacao.validadores.ValidadorMovimentacao;
import conexao_parking.api.domain.veiculo.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final VeiculoRepository veiculoRepository;
    private final List<ValidadorMovimentacao<DadosMovimentacaoEntrada>> validadoresEntrada;
    private final List<ValidadorMovimentacao<DadosMovimentacaoSaida>> validadoresSaida;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository,
                               VeiculoRepository veiculoRepository,
                               List <ValidadorMovimentacao<DadosMovimentacaoEntrada>> validadoresEntrada,
                               List <ValidadorMovimentacao<DadosMovimentacaoSaida>> validadoresSaida){
        this.movimentacaoRepository = movimentacaoRepository;
        this.veiculoRepository = veiculoRepository;
        this.validadoresEntrada = validadoresEntrada;
        this.validadoresSaida = validadoresSaida;
    }

    public Movimentacao liberarEntrada(DadosMovimentacaoEntrada dados) {
        validadoresEntrada.forEach(v -> v.validar(dados));
        var veiculo = veiculoRepository.getReferenceById(dados.idVeiculo());

        var movimentacao = new Movimentacao(veiculo, LocalDateTime.now(), null, dados.observacaoEntrada(), null);
        movimentacaoRepository.save(movimentacao);

        return movimentacao;
    }

    public Movimentacao liberarSaida(DadosMovimentacaoSaida dados) {
        validadoresSaida.forEach(v -> v.validar(dados));

        var movimentacao = movimentacaoRepository.findById(dados.idMovimentacao()).get();

        movimentacao.setDataSaida(LocalDateTime.now());
        movimentacao.setObservacaoSaida(dados.observacaoSaida());
        movimentacaoRepository.save(movimentacao);

        return movimentacao;
    }

}
