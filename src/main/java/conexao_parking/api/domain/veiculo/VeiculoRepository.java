package conexao_parking.api.domain.veiculo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    Page<Veiculo> findByExcluidoFalse(Pageable paginacao);

    boolean existsByIdVeiculoAndBloqueadoTrue(Long idVeiculo);
}
