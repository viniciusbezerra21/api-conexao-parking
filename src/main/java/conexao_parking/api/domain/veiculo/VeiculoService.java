package conexao_parking.api.domain.veiculo;

import conexao_parking.api.domain.proprietario.Proprietario;
import conexao_parking.api.domain.proprietario.ProprietarioRepository;
import org.springframework.stereotype.Service;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ProprietarioRepository proprietarioRepository;

    public VeiculoService(
        VeiculoRepository veiculoRepository,
        ProprietarioRepository proprietarioRepository
    ) {
        this.veiculoRepository = veiculoRepository;
        this.proprietarioRepository = proprietarioRepository;
    }

    public Veiculo cadastrar(DadosCadastroVeiculo dados) {
        Proprietario proprietario =
                proprietarioRepository.getReferenceById(dados.id_proprietario());

        Veiculo veiculo = new Veiculo(dados, proprietario);

        return veiculoRepository.save(veiculo);
    }


}
