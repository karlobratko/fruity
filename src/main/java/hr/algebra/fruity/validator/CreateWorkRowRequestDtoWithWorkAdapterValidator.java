package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.CreateWorkRowRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.WorkRowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkRowRequestDtoWithWorkAdapterValidator implements Validator<CreateWorkRowRequestDtoWithWorkAdapter> {

  private final WorkRowRepository workRowRepository;

  @Override
  public void validate(CreateWorkRowRequestDtoWithWorkAdapter target) {
    if (workRowRepository.existsByWorkIdAndRowId(target.work().getId(), target.dto().rowFk()))
      throw new UniquenessViolatedException("Red je već uključen u radu.");
  }

}
