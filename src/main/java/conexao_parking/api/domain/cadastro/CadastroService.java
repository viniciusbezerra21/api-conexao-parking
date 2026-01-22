package conexao_parking.api.domain.cadastro;

import conexao_parking.api.domain.usuario.Usuario;
import conexao_parking.api.domain.usuario.UsuarioRepository;
import conexao_parking.api.domain.veiculo.Veiculo;
import conexao_parking.api.domain.veiculo.VeiculoRepository;
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
