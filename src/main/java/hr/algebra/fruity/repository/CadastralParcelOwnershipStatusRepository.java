package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.CadastralParcelOwnershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadastralParcelOwnershipStatusRepository extends JpaRepository<CadastralParcelOwnershipStatus, Integer> {
}
