package hr.algebra.fruity.validator;

import hr.algebra.fruity.constraints.UniqueEmail;
import hr.algebra.fruity.repository.EmployeeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

  private final EmployeeRepository employeeRepository;

  @Override
  public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
    return !employeeRepository.existsByEmail(email);
  }

}