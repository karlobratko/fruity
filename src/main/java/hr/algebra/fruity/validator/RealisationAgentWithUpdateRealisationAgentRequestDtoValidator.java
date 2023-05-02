package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.UpdateRealisationAgentRequestDto;
import hr.algebra.fruity.exception.WorkAlreadyFinishedException;
import hr.algebra.fruity.model.RealisationAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealisationAgentWithUpdateRealisationAgentRequestDtoValidator implements WithValidator<RealisationAgent, UpdateRealisationAgentRequestDto> {

  @Override
  public void validate(RealisationAgent target, UpdateRealisationAgentRequestDto with) {
    if (target.getRealisation().getWork().isFinished())
      throw new WorkAlreadyFinishedException();
  }

}
