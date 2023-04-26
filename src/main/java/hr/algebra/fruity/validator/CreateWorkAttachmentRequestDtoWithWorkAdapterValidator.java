package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.CreateWorkAttachmentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.WorkAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkAttachmentRequestDtoWithWorkAdapterValidator implements Validator<CreateWorkAttachmentRequestDtoWithWorkAdapter> {

  private final WorkAttachmentRepository workAttachmentRepository;

  @Override
  public void validate(CreateWorkAttachmentRequestDtoWithWorkAdapter target) {
    if (workAttachmentRepository.existsByWorkIdAndAttachmentId(target.work().getId(), target.dto().attachmentFk()))
      throw new UniquenessViolatedException("Priključak je već korišten u radu.");
  }

}
