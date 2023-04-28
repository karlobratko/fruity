package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.model.User;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.model.WorkEquipment;
import hr.algebra.fruity.model.WorkEquipment.WorkEquipmentId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkEquipmentRepository extends JpaRepository<WorkEquipment, WorkEquipmentId> {

  Optional<WorkEquipment> findByWorkIdAndEquipmentIdAndWorkUserId(Long workFk, Long equipmentFk, Long userFk);

  Optional<WorkEquipment> findByWorkAndEquipmentAndWorkUser(Work work, Equipment equipment, User user);

  List<WorkEquipment> findAllByWorkId(Long workFk);

  List<WorkEquipment> findAllByWork(Work work);

  boolean existsByWorkIdAndEquipmentId(Long workFk, Long equipmentFk);

  boolean existsByWorkAndEquipment(Work work, Equipment equipment);

}
