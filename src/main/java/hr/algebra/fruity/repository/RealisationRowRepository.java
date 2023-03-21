package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.RealisationRow;
import hr.algebra.fruity.model.RealisationRow.RealisationRowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealisationRowRepository extends JpaRepository<RealisationRow, RealisationRowId> {
}
