package conexao_parking.api.cadastro;

import conexao_parking.api.usuario.Usuario;
import conexao_parking.api.usuario.UsuarioRepository;
import conexao_parking.api.veiculo.Veiculo;
import conexao_parking.api.veiculo.VeiculoRepository;
import org.springframework.stereotype.Service;

@Service
public class CadastroService {

    private final CadastroRepository cadastroRepository;
    private final UsuarioRepository usuarioRepository;
    private final VeiculoRepository veiculoRepository;

    public CadastroService (
            CadastroRepository cadastroRepository,
            UsuarioRepository usuarioRepository,
            VeiculoRepository veiculoRepository
    ) {
        this.cadastroRepository = cadastroRepository;
        this.usuarioRepository = usuarioRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public void cadastrar(CadastroRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.id_usuario())
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        Veiculo veiculo = veiculoRepository.findById(dto.id_veiculo())
                .orElseThrow(() -> new RuntimeException("Veiculo não Encontrado"));

        Cadastro cadastro = new Cadastro(usuario, veiculo);
        cadastroRepository.save(cadastro);
    }
}
