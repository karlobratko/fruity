package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.RealisationEquipment;
import hr.algebra.fruity.model.RealisationEquipment.RealisationEquipmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealisationEquipmentRepository extends JpaRepository<RealisationEquipment, RealisationEquipmentId> {
}
