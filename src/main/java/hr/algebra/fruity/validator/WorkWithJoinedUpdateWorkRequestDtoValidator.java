package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedUpdateWorkRequestDto;
import hr.algebra.fruity.exception.InvalidTimePointsException;
import hr.algebra.fruity.model.Work;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class WorkWithJoinedUpdateWorkRequestDtoValidator implements WithValidator<Work, JoinedUpdateWorkRequestDto> {

  @Override
  public void validate(Work target, JoinedUpdateWorkRequestDto with) {
    if ((Objects.nonNull(with.startDateTime()) && Objects.nonNull(with.endDateTime()) && with.endDateTime().isBefore(with.startDateTime()))
      || (Objects.nonNull(with.startDateTime()) && target.getEndDateTime().isBefore(with.startDateTime()))
      || (Objects.nonNull(with.endDateTime()) && target.getStartDateTime().isAfter(with.endDateTime())))
      throw new InvalidTimePointsException();
  }

}
