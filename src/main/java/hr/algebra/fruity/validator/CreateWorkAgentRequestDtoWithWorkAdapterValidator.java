package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.CreateWorkAgentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.WorkAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkAgentRequestDtoWithWorkAdapterValidator implements Validator<CreateWorkAgentRequestDtoWithWorkAdapter> {

  private final WorkAgentRepository workAgentRepository;

  @Override
  public void validate(CreateWorkAgentRequestDtoWithWorkAdapter target) {
    if (workAgentRepository.existsByWorkIdAndAgentId(target.work().getId(), target.dto().agentFk()))
      throw new UniquenessViolatedException("Sredstvo je već korišteno u radu.");
  }

}
