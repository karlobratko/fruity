package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.MobileToken;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileTokenRepository extends JpaRepository<MobileToken, Long> {

  Optional<MobileToken> findByUuid(UUID uuid);

}
