package conexao_parking.api.domain.movimentacao;

import conexao_parking.api.domain.veiculo.Veiculo;
import conexao_parking.api.domain.veiculo.VeiculoRepository;
import org.springframework.stereotype.Service;

@Service
public class MoviementacaoService {

    private MovimentacaoRepository movimentacaoRepository;
    private VeiculoRepository veiculoRepository;

    public MoviementacaoService(
            MovimentacaoRepository movimentacaoRepository,
            VeiculoRepository veiculoRepository
    ){
        this.movimentacaoRepository = movimentacaoRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public Movimentacao cadastrar(DadosCadastroMovimentacao dados) {
        Veiculo veiculo = veiculoRepository.getReferenceById(dados.id_veiculo());

        Movimentacao movimentacao = new Movimentacao(dados, veiculo);

        return movimentacaoRepository.save(movimentacao);
    }
}
