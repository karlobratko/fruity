package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.RealisationAttachment;
import hr.algebra.fruity.model.RealisationAttachment.RealisationAttachmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealisationAttachmentRepository extends JpaRepository<RealisationAttachment, RealisationAttachmentId> {
}
