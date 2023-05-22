package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.model.User;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.model.WorkRow;
import hr.algebra.fruity.model.WorkRow.WorkRowId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRowRepository extends JpaRepository<WorkRow, WorkRowId> {

  Optional<WorkRow> findByWorkIdAndRowIdAndWorkUserId(Long workFk, Long rowFk, Long userFk);

  Optional<WorkRow> findByWorkAndRowAndWorkUser(Work work, Row row, User user);

  List<WorkRow> findAllByWorkId(Long workFk);

  List<WorkRow> findAllByWork(Work work);

  List<WorkRow> findAllByWorkIdOrderByRowRowClusterAscRowOrdinalAsc(Long workFk);

  List<WorkRow> findAllByWorkOrderByRowRowClusterAscRowOrdinalAsc(Work work);

  boolean existsByWorkIdAndRowId(Long workFk, Long rowFk);

  boolean existsByWorkAndRow(Work work, Row row);

  void deleteByRowIdAndWorkFinishedFalse(Long rowFk);

  void deleteByRowAndWorkFinishedFalse(Row row);

}
