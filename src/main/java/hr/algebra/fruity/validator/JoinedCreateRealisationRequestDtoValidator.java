package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationRequestDto;
import hr.algebra.fruity.exception.InvalidTimePointsException;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.exception.WorkAlreadyFinishedException;
import hr.algebra.fruity.repository.RealisationRepository;
import hr.algebra.fruity.service.EmployeeService;
import hr.algebra.fruity.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateRealisationRequestDtoValidator implements Validator<JoinedCreateRealisationRequestDto> {

  private final WorkService workService;

  private final EmployeeService employeeService;

  private final RealisationRepository realisationRepository;

  @Override
  public void validate(JoinedCreateRealisationRequestDto target) {
    if (target.work().isFinished())
      throw new WorkAlreadyFinishedException();

    if (realisationRepository.existsByWorkAndEmployeeAndStartDateTimeAndEndDateTime(target.work(), target.employee(), target.startDateTime(), target.endDateTime()))
      throw new UniquenessViolatedException("Realizacija za traženi rad, radnika te vrijeme početka i završetka već postoji.");

    if (target.startDateTime().isAfter(target.endDateTime()))
      throw new InvalidTimePointsException();
  }

}
