package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.Realisation;
import hr.algebra.fruity.model.RealisationAttachment;
import hr.algebra.fruity.model.RealisationAttachment.RealisationAttachmentId;
import hr.algebra.fruity.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealisationAttachmentRepository extends JpaRepository<RealisationAttachment, RealisationAttachmentId> {

  Optional<RealisationAttachment> findByRealisationIdAndAttachmentIdAndRealisationWorkUserId(Long realisationFk, Long attachmentFk, Long userFk);

  Optional<RealisationAttachment> findByRealisationAndAttachmentAndRealisationWorkUser(Realisation realisation, Attachment attachment, User user);

  List<RealisationAttachment> findAllByRealisationId(Long realisationFk);

  List<RealisationAttachment> findAllByRealisation(Realisation realisation);

  boolean existsByRealisationIdAndAttachmentId(Long realisationFk, Long attachmentFk);

  boolean existsByRealisationAndAttachment(Realisation realisation, Attachment attachment);

  void deleteByAttachmentIdAndRealisationWorkFinishedFalse(Long attachmentFk);

  void deleteByAttachmentAndRealisationWorkFinishedFalse(Attachment attachment);

}
