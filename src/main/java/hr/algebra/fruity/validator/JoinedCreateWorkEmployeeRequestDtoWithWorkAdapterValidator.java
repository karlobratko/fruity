package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkEmployeeRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.WorkEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateWorkEmployeeRequestDtoWithWorkAdapterValidator implements Validator<JoinedCreateWorkEmployeeRequestDtoWithWorkAdapter> {

  private final WorkEmployeeRepository workEmployeeRepository;

  @Override
  public void validate(JoinedCreateWorkEmployeeRequestDtoWithWorkAdapter target) {
    if (workEmployeeRepository.existsByWorkAndEmployee(target.work(), target.dto().employee()))
      throw new UniquenessViolatedException("Zaposlenik već sudjeluje u radu.");
  }

}
