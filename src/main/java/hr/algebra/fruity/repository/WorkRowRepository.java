package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.WorkRow;
import hr.algebra.fruity.model.WorkRow.WorkRowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRowRepository extends JpaRepository<WorkRow, WorkRowId> {
}
