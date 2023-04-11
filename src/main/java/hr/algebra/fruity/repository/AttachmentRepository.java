package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.model.User;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends PagingAndSortingRepository<Attachment, Long>, JpaRepository<Attachment, Long> {

  Set<Attachment> findAllByUser(User user);

  Set<Attachment> findAllByUserId(Long userFk);

  Optional<Attachment> findByNameAndUser(String name, User user);

  Optional<Attachment> findByNameAndUserId(String name, Long userFk);

}
