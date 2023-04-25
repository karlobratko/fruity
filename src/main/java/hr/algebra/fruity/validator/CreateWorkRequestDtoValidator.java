package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.CreateWorkRequestDto;
import hr.algebra.fruity.exception.InvalidTimePointsException;
import org.springframework.stereotype.Component;

@Component
public class CreateWorkRequestDtoValidator implements Validator<CreateWorkRequestDto> {

  @Override
  public void validate(CreateWorkRequestDto target) {
    if (target.endDateTime().isAfter(target.startDateTime()))
      throw new InvalidTimePointsException();
  }

}
