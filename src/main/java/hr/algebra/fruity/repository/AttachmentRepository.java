package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.model.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends PagingAndSortingRepository<Attachment, Long>, JpaRepository<Attachment, Long> {

  Optional<Attachment> findByIdAndUser(Long id, User user);

  Optional<Attachment> findByIdAndUserId(Long id, Long userFk);

  @Query(
    value = "SELECT a " +
      "FROM Attachment a " +
      "WHERE a.id IN :ids " +
      "  AND a.user.id = :userFk " +
      "ORDER BY a.id ASC"
  )
  List<Attachment> findAllByIdsAndUserId(List<Long> ids, Long userFk);

  @Query(
    value = "SELECT a " +
      "FROM Attachment a " +
      "WHERE a.id IN :ids " +
      "  AND a.user = :user " +
      "ORDER BY a.id ASC"
  )
  List<Attachment> findAllByIdsAndUser(List<Long> ids, User user);

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
