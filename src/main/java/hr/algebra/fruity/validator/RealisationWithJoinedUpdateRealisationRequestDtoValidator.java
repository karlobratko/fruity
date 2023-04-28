package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedUpdateRealisationRequestDto;
import hr.algebra.fruity.exception.InvalidTimePointsException;
import hr.algebra.fruity.exception.NonManagerUpdateRealisationEmployeeException;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.exception.WorkAlreadyFinishedException;
import hr.algebra.fruity.model.Realisation;
import hr.algebra.fruity.model.codebook.EmployeeRoles;
import hr.algebra.fruity.repository.RealisationRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.EmployeeRoleService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealisationWithJoinedUpdateRealisationRequestDtoValidator implements WithValidator<Realisation, JoinedUpdateRealisationRequestDto> {

  private final RealisationRepository realisationRepository;

  private final CurrentRequestUserService currentRequestUserService;

  private final EmployeeRoleService employeeRoleService;

  @Override
  public void validate(Realisation target, JoinedUpdateRealisationRequestDto with) {
    if (target.getWork().isFinished())
      throw new WorkAlreadyFinishedException();

    if (!currentRequestUserService.getEmployee().getRole().equals(employeeRoleService.getEmployeeRole(EmployeeRoles.ROLE_MANAGER)))
      throw new NonManagerUpdateRealisationEmployeeException();

    if (Objects.nonNull(with.employee()) || Objects.nonNull(with.startDateTime()) || Objects.nonNull(with.endDateTime()))
      realisationRepository.findByWorkAndEmployeeAndStartDateTimeAndEndDateTime(
          target.getWork(),
          Objects.requireNonNullElse(with.employee(), target.getEmployee()),
          Objects.requireNonNullElse(with.startDateTime(), target.getStartDateTime()),
          Objects.requireNonNullElse(with.endDateTime(), target.getEndDateTime())
        )
        .ifPresent(it -> {
          if (!Objects.equals(it, target))
            throw new UniquenessViolatedException("Realizacija za traženi rad, radnika te vrijeme početka i završetka već postoji.");
        });

    if ((Objects.nonNull(with.startDateTime()) && target.getEndDateTime().isBefore(with.startDateTime()))
      || (Objects.nonNull(with.endDateTime()) && target.getStartDateTime().isAfter(with.endDateTime()))
      || (Objects.nonNull(with.startDateTime()) && Objects.nonNull(with.endDateTime()) && with.endDateTime().isBefore(with.startDateTime())))
      throw new InvalidTimePointsException();
  }

}
