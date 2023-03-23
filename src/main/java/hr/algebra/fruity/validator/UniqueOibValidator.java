package hr.algebra.fruity.validator;

import hr.algebra.fruity.constraints.UniqueOib;
import hr.algebra.fruity.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueOibValidator implements ConstraintValidator<UniqueOib, String> {

  private final UserRepository userRepository;

  @Override
  public boolean isValid(String oib, ConstraintValidatorContext constraintValidatorContext) {
    return !userRepository.existsByOib(oib);
  }

}
