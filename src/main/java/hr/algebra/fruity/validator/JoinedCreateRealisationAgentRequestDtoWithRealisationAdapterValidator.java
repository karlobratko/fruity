package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationAgentRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.exception.WorkAlreadyFinishedException;
import hr.algebra.fruity.repository.RealisationAgentRepository;
import hr.algebra.fruity.repository.WorkAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateRealisationAgentRequestDtoWithRealisationAdapterValidator implements Validator<JoinedCreateRealisationAgentRequestDtoWithRealisationAdapter> {

  private final RealisationAgentRepository realisationAgentRepository;

  private final WorkAgentRepository workAgentRepository;

  @Override
  public void validate(JoinedCreateRealisationAgentRequestDtoWithRealisationAdapter target) {
    if (target.realisation().getWork().isFinished())
      throw new WorkAlreadyFinishedException();

    if (realisationAgentRepository.existsByRealisationAndAgent(target.realisation(), target.dto().agent()))
      throw new UniquenessViolatedException("Sredstvo je već korišteno u realizaciji.");
  }

}
