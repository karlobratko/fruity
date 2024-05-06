package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.UpdateAttachmentRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.repository.AttachmentRepository;
import jakarta.persistence.EntityManager;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttachmentWithUpdateAttachmentRequestDtoValidator implements WithValidator<Attachment, UpdateAttachmentRequestDto> {

  private final AttachmentRepository attachmentRepository;

  private final EntityManager entityManager;

  @Override
  public void validate(Attachment target, UpdateAttachmentRequestDto with) {
    val session = entityManager.unwrap(Session.class);
    val filter = session.enableFilter("isNotDeleted");

    if (Objects.nonNull(with.name()))
      attachmentRepository.findByNameAndUser(with.name(), target.getUser())
        .ifPresent(it -> {
          if (!Objects.equals(it, target))
            throw new UniquenessViolatedException("Naziv već postoji i nije jedinstven.");
        });

    session.disableFilter("isNotDeleted");
  }

}
