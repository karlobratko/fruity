package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.exception.WorkAlreadyFinishedException;
import hr.algebra.fruity.repository.RealisationAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterValidator implements Validator<JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapter> {

  private final RealisationAttachmentRepository workAttachmentRepository;

  @Override
  public void validate(JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapter target) {
    if (target.realisation().getWork().isFinished())
      throw new WorkAlreadyFinishedException();

    if (workAttachmentRepository.existsByRealisationAndAttachment(target.realisation(), target.dto().attachment()))
      throw new UniquenessViolatedException("Priključak je već korišten u realizaciji.");
  }

}
