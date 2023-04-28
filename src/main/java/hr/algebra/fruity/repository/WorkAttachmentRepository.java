package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.model.User;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.model.WorkAttachment;
import hr.algebra.fruity.model.WorkAttachment.WorkAttachmentId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkAttachmentRepository extends JpaRepository<WorkAttachment, WorkAttachmentId> {

  Optional<WorkAttachment> findByWorkIdAndAttachmentIdAndWorkUserId(Long workFk, Long attachmentFk, Long userFk);

  Optional<WorkAttachment> findByWorkAndAttachmentAndWorkUser(Work work, Attachment attachment, User user);

  List<WorkAttachment> findAllByWorkId(Long workFk);

  List<WorkAttachment> findAllByWork(Work work);

  boolean existsByWorkIdAndAttachmentId(Long workFk, Long attachmentFk);

  boolean existsByWorkAndAttachment(Work work, Attachment attachment);


}
