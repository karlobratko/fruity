package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkAttachmentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.WorkAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateWorkAttachmentRequestDtoWithWorkAdapterValidator implements Validator<JoinedCreateWorkAttachmentRequestDtoWithWorkAdapter> {

  private final WorkAttachmentRepository workAttachmentRepository;

  @Override
  public void validate(JoinedCreateWorkAttachmentRequestDtoWithWorkAdapter target) {
    if (workAttachmentRepository.existsByWorkAndAttachment(target.work(), target.dto().attachment()))
      throw new UniquenessViolatedException("Priključak je već korišten u radu.");
  }

}
