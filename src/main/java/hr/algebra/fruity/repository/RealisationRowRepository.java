package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Realisation;
import hr.algebra.fruity.model.RealisationRow;
import hr.algebra.fruity.model.RealisationRow.RealisationRowId;
import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealisationRowRepository extends JpaRepository<RealisationRow, RealisationRowId> {

  Optional<RealisationRow> findByRealisationIdAndRowIdAndRealisationWorkUserId(Long workFk, Long rowFk, Long userFk);

  Optional<RealisationRow> findByRealisationAndRowAndRealisationWorkUser(Realisation work, Row row, User user);

  List<RealisationRow> findAllByRealisationId(Long workFk);

  List<RealisationRow> findAllByRealisation(Realisation work);

  List<RealisationRow> findAllByRealisationIdOrderByRowRowClusterAscRowOrdinalAsc(Long workFk);

  List<RealisationRow> findAllByRealisationOrderByRowRowClusterAscRowOrdinalAsc(Realisation work);

  boolean existsByRealisationIdAndRowId(Long workFk, Long rowFk);

  boolean existsByRealisationAndRow(Realisation work, Row row);

  void deleteByRowIdAndRealisationWorkFinishedFalse(Long rowFk);

  void deleteByRowAndRealisationWorkFinishedFalse(Row row);

}
