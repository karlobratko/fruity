package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.WorkAttachment;
import hr.algebra.fruity.model.WorkAttachment.WorkAttachmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkAttachmentRepository extends JpaRepository<WorkAttachment, WorkAttachmentId> {
}
