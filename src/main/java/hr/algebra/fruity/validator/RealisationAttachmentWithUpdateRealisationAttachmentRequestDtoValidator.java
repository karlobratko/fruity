package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.UpdateRealisationAttachmentRequestDto;
import hr.algebra.fruity.exception.WorkAlreadyFinishedException;
import hr.algebra.fruity.model.RealisationAttachment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealisationAttachmentWithUpdateRealisationAttachmentRequestDtoValidator implements WithValidator<RealisationAttachment, UpdateRealisationAttachmentRequestDto> {

  @Override
  public void validate(RealisationAttachment target, UpdateRealisationAttachmentRequestDto with) {
    if (target.getRealisation().getWork().isFinished())
      throw new WorkAlreadyFinishedException();
  }

}
