package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.UpdateAttachmentRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.repository.AttachmentRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttachmentWithUpdateAttachmentRequestDtoValidator implements WithValidator<Attachment, UpdateAttachmentRequestDto> {

  private final AttachmentRepository attachmentRepository;

  @Override
  public void validate(Attachment target, UpdateAttachmentRequestDto with) {
    if (Objects.nonNull(with.name()))
      attachmentRepository.findByNameAndUser(with.name(), target.getUser())
        .ifPresent(it -> {
          if (!Objects.equals(it, target))
            throw new UniquenessViolatedException("Naziv već postoji i nije jedinstven.");
        });
  }

}
