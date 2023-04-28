package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkAgentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.WorkAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateWorkAgentRequestDtoWithWorkAdapterValidator implements Validator<JoinedCreateWorkAgentRequestDtoWithWorkAdapter> {

  private final WorkAgentRepository workAgentRepository;

  @Override
  public void validate(JoinedCreateWorkAgentRequestDtoWithWorkAdapter target) {
    if (workAgentRepository.existsByWorkAndAgent(target.work(), target.dto().agent()))
      throw new UniquenessViolatedException("Sredstvo je već korišteno u radu.");
  }

}
