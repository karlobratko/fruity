package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.RegistrationToken;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {

  Optional<RegistrationToken> findByUuid(UUID uuid);

}
