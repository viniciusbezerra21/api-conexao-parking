package conexao_parking.api.domain.movimentacao;

import conexao_parking.api.domain.veiculo.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final VeiculoRepository veiculoRepository;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository, VeiculoRepository veiculoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public Movimentacao liberarEntrada(DadosMovimentacaoEntrada dados) {
        boolean veiculoBloqueado = veiculoRepository.existsByIdVeiculoAndBloqueadoTrue(dados.idVeiculo());
        if (veiculoBloqueado) {
            throw new IllegalStateException("Veiculo bloqueado!");
        }

        boolean veiculoJaEntrou = movimentacaoRepository.existsByVeiculoIdVeiculoAndDataSaidaIsNull(dados.idVeiculo());

        if  (veiculoJaEntrou) {
            throw new IllegalStateException("Veiculo ja possui entrada ativa");
        }

        var veiculo = veiculoRepository.getReferenceById(dados.idVeiculo());

        var movimentacao = new Movimentacao(veiculo, LocalDateTime.now(), null, dados.observacaoEntrada(), null);
        movimentacaoRepository.save(movimentacao);

        return movimentacao;
    }

    public Movimentacao liberarSaida(DadosMovimentacaoSaida dados) {
        var movimentacao = movimentacaoRepository.findByIdAndDataSaidaIsNull(dados.idMovimentacao())
                .orElseThrow(() -> new IllegalStateException("Veiculo já saiu"));

        movimentacao.setDataSaida(LocalDateTime.now());
        movimentacao.setObservacaoSaida(dados.observacaoSaida());
        movimentacaoRepository.save(movimentacao);

        return movimentacao;
    }

}
