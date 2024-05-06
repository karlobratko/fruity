package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkRequestDto;
import hr.algebra.fruity.exception.InvalidTimePointsException;
import org.springframework.stereotype.Component;

@Component
public class JoinedCreateWorkRequestDtoValidator implements Validator<JoinedCreateWorkRequestDto> {

  @Override
  public void validate(JoinedCreateWorkRequestDto target) {
    if (target.startDateTime().isAfter(target.endDateTime()))
      throw new InvalidTimePointsException();
  }

}
