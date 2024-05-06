package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.CreateEmployeeRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.EmployeeRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateEmployeeRequestDtoValidator implements Validator<CreateEmployeeRequestDto> {

  private final EmployeeRepository employeeRepository;

  private final CurrentRequestUserService currentRequestUserService;

  @Override
  public void validate(CreateEmployeeRequestDto target) {
    if (Objects.nonNull(target.email()) && employeeRepository.existsByEmailAndUserId(target.email(), currentRequestUserService.getUserId()))
      throw new UniquenessViolatedException("Email već postoji i nije jedinstven.");
  }

}
