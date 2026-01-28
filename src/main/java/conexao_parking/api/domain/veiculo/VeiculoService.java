package conexao_parking.api.domain.veiculo;

import conexao_parking.api.domain.cadastro.Cadastro;
import conexao_parking.api.domain.cadastro.CadastroRepository;
import conexao_parking.api.domain.proprietario.Proprietario;
import conexao_parking.api.domain.proprietario.ProprietarioRepository;
import conexao_parking.api.domain.usuario.UsuarioAutenticadoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ProprietarioRepository proprietarioRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final CadastroRepository cadastroRepository;

    public VeiculoService(
        VeiculoRepository veiculoRepository,
        ProprietarioRepository proprietarioRepository,
        UsuarioAutenticadoService usuarioAutenticadoService,
        CadastroRepository cadastroRepository
    ) {
        this.veiculoRepository = veiculoRepository;
        this.proprietarioRepository = proprietarioRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.cadastroRepository = cadastroRepository;
    }

    @Transactional
    public Veiculo cadastrar(DadosCadastroVeiculo dados) {
        var proprietario = new Proprietario(dados.proprietario());
        proprietarioRepository.save(proprietario);

        var usuario = usuarioAutenticadoService.get();

        var veiculo = new Veiculo(dados, proprietario);
        veiculoRepository.save(veiculo);

        var cadastro = new Cadastro(usuario, veiculo, LocalDateTime.now());
        cadastroRepository.save(cadastro);

        return veiculo;
    }


}
