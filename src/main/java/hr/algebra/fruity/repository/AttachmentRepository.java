package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.model.User;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends PagingAndSortingRepository<Attachment, Long>, JpaRepository<Attachment, Long> {

  Set<Attachment> findAllByUser(User user);

  Set<Attachment> findAllByUserId(Long userFk);

  @Query(
    value = "SELECT a " +
      "FROM Attachment a " +
      "INNER JOIN Equipment e " +
      "WHERE a.id = :equipmentId " +
      "ORDER BY a.id ASC"
  )
  Set<Attachment> findAllByEquipmentId(Long equipmentId);

  @Query(
    value = "SELECT a " +
      "FROM Attachment a " +
      "INNER JOIN Equipment e " +
      "WHERE a.id = :#{#equipment.id} " +
      "ORDER BY a.id ASC"
  )
  Set<Attachment> findAllByEquipment(Equipment equipment);

  Optional<Attachment> findByNameAndUser(String name, User user);

  Optional<Attachment> findByNameAndUserId(String name, Long userFk);

}
