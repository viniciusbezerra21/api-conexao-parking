package conexao_parking.api.status;


import conexao_parking.api.veiculo.Veiculo;
import conexao_parking.api.veiculo.VeiculoRepository;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    private final StatusRepository statusRepository;
    private final VeiculoRepository veiculoRepository;

    public StatusService(
            StatusRepository statusRepository,
            VeiculoRepository veiculoRepository
    ) {
        this.statusRepository = statusRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public Status cadastrar(DadaosCadastroStatus dados) {
        Veiculo veiculo =
                veiculoRepository.getReferenceById(dados.id_veiculo());

        Status status = new Status(dados, veiculo);

        return statusRepository.save(status);
    }

}
