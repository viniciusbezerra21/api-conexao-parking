package conexao_parking.api.domain.movimentacao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    Optional<Movimentacao> findByIdAndDataSaidaIsNull(Long id);

    boolean existsByVeiculoIdVeiculoAndDataSaidaIsNull(Long idVeiculo);
}
