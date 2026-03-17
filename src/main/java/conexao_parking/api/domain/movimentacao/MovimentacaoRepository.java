package conexao_parking.api.domain.movimentacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    Optional<Movimentacao> findByIdAndDataSaidaIsNull(Long id);

    boolean existsByVeiculoIdVeiculoAndDataSaidaIsNull(Long idVeiculo);

    Page<Movimentacao> findAllByDataEntradaAfter(LocalDateTime data, Pageable pageable);
}
