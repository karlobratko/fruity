package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.WorkEquipment;
import hr.algebra.fruity.model.WorkEquipment.WorkEquipmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkEquipmentRepository extends JpaRepository<WorkEquipment, WorkEquipmentId> {
}
