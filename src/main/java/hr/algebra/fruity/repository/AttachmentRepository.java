package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends PagingAndSortingRepository<Attachment, Long>, JpaRepository<Attachment, Long> {
}
