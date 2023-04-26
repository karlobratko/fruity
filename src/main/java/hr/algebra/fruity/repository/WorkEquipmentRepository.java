package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.model.WorkEquipment;
import hr.algebra.fruity.model.WorkEquipment.WorkEquipmentId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkEquipmentRepository extends JpaRepository<WorkEquipment, WorkEquipmentId> {

  Optional<WorkEquipment> findByWorkIdAndEquipmentId(Long workFk, Long equipmentFk);

  Optional<WorkEquipment> findByWorkAndEquipment(Work work, Equipment equipment);

  List<WorkEquipment> findAllByWorkId(Long workFk);

  List<WorkEquipment> findAllByWork(Work work);

  boolean existsByWorkIdAndEquipmentId(Long workFk, Long equipmentFk);

  boolean existsByWorkAndEquipment(Work work, Equipment equipment);

}
