package conexao_parking.api.domain.proprietario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {

    Optional<Proprietario> findByCpfProprietario(String cpf);
}
