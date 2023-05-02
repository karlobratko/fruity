package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.model.Realisation;
import hr.algebra.fruity.model.RealisationEquipment;
import hr.algebra.fruity.model.RealisationEquipment.RealisationEquipmentId;
import hr.algebra.fruity.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealisationEquipmentRepository extends JpaRepository<RealisationEquipment, RealisationEquipmentId> {

  Optional<RealisationEquipment> findByRealisationIdAndEquipmentIdAndRealisationWorkUserId(Long workFk, Long equipmentFk, Long userFk);

  Optional<RealisationEquipment> findByRealisationAndEquipmentAndRealisationWorkUser(Realisation work, Equipment equipment, User user);

  List<RealisationEquipment> findAllByRealisationId(Long workFk);

  List<RealisationEquipment> findAllByRealisation(Realisation work);

  boolean existsByRealisationIdAndEquipmentId(Long workFk, Long equipmentFk);

  boolean existsByRealisationAndEquipment(Realisation work, Equipment equipment);

}
