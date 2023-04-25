package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.UpdateWorkRequestDto;
import hr.algebra.fruity.exception.InvalidTimePointsException;
import hr.algebra.fruity.model.Work;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class WorkWithUpdateWorkRequestDtoValidator implements WithValidator<Work, UpdateWorkRequestDto> {

  @Override
  public void validate(Work target, UpdateWorkRequestDto with) {
    if ((Objects.nonNull(with.startDateTime()) && target.getEndDateTime().isBefore(with.startDateTime()))
      || (Objects.nonNull(with.endDateTime()) && target.getStartDateTime().isAfter(with.endDateTime()))
      || (Objects.nonNull(with.startDateTime()) && Objects.nonNull(with.endDateTime()) && with.endDateTime().isBefore(with.startDateTime())))
      throw new InvalidTimePointsException();
  }

}
