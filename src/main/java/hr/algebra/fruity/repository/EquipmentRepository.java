package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.model.User;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends PagingAndSortingRepository<Equipment, Long>, JpaRepository<Equipment, Long> {

  Optional<Equipment> findByIdAndUser(Long id, User user);

  Optional<Equipment> findByIdAndUserId(Long id, Long userFk);

  Set<Equipment> findAllByUser(User user);

  Set<Equipment> findAllByUserId(Long userFk);

  Optional<Equipment> findByNameAndUser(String name, User user);

  Optional<Equipment> findByNameAndUserId(String name, Long userFk);

}
