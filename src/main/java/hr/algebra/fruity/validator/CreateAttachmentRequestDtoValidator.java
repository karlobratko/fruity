package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.CreateAttachmentRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.AttachmentRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAttachmentRequestDtoValidator implements Validator<CreateAttachmentRequestDto> {

  private final AttachmentRepository attachmentRepository;

  private final CurrentRequestUserService currentRequestUserService;

  @Override
  public void validate(CreateAttachmentRequestDto target) {
    attachmentRepository.findByNameAndUserId(target.name(), currentRequestUserService.getUserId())
      .ifPresent(ignored -> {
        throw new UniquenessViolatedException("Naziv već postoji i nije jedinstven.");
      });
  }

}