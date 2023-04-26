package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.CreateWorkEmployeeRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.WorkEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkEmployeeRequestDtoWithWorkAdapterValidator implements Validator<CreateWorkEmployeeRequestDtoWithWorkAdapter> {

  private final WorkEmployeeRepository workEmployeeRepository;

  @Override
  public void validate(CreateWorkEmployeeRequestDtoWithWorkAdapter target) {
    if (workEmployeeRepository.existsByWorkIdAndEmployeeId(target.work().getId(), target.dto().employeeFk()))
      throw new UniquenessViolatedException("Zaposlenik već sudjeluje u radu.");
  }

}
