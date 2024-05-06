package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.UpdateEmployeeRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.repository.EmployeeRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeWithUpdateEmployeeRequestDtoValidator implements WithValidator<Employee, UpdateEmployeeRequestDto> {

  private final EmployeeRepository employeeRepository;

  @Override
  public void validate(Employee target, UpdateEmployeeRequestDto with) {
    if (Objects.nonNull(with.email()))
      employeeRepository.findByEmailAndUser(with.email(), target.getUser())
        .ifPresent(it -> {
          if (!Objects.equals(it, target))
            throw new UniquenessViolatedException("Email već postoji i nije jedinstven.");
        });
  }

}
