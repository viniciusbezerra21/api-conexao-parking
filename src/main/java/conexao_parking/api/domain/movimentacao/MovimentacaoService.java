package conexao_parking.api.domain.movimentacao;

import conexao_parking.api.domain.veiculo.Veiculo;
import conexao_parking.api.domain.veiculo.VeiculoRepository;
import org.springframework.cglib.core.Local;
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

    public void liberarEntrada(DadosMovimentacaoEntrada dados) {
        boolean veiculoBloqueado = veiculoRepository.existsByIdAndBloqueadoTrue(dados.idVeiculo());
        if (veiculoBloqueado) {
            throw new IllegalStateException("Veiculo bloqueado");
        }

        boolean veiculoJaEntrou = veiculoRepository.existsByIdAndData_saidaIsNull(dados.idVeiculo()) {
            throw new IllegalStateException("Veiculo ja possui entrada ativa");
        }

        var veiculo = veiculoRepository.getReferenceById(dados.idVeiculo());

        var movimentacao = new Movimentacao(veiculo, LocalDateTime.now(), null, dados.observacaoEntrada(), null);

        movimentacaoRepository.save(movimentacao);
    }

    public void liberarSaida(DadosMovimentacaoSaida dados) {
        var veiculoJaSaiu = veiculoRepository.existsByIdAndData_saidaIsNull(dados.idMovimentacao());
        if (!veiculoJaSaiu) {
            throw new IllegalStateException("Veiculo Ja saiu");
        }
    }

}
