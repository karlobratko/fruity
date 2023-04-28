package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.joined.JoinedUpdateRealisationRequestDto;
import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.utils.mother.model.EmployeeMother;
import hr.algebra.fruity.utils.mother.model.WorkMother;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinedUpdateRealisationRequestDtoMother {

  public static JoinedUpdateRealisationRequestDto.JoinedUpdateRealisationRequestDtoBuilder complete() {
    return JoinedUpdateRealisationRequestDto.builder()
      .employee(Constants.instanceEmployee)
      .startDateTime(Constants.instanceStartDateTime)
      .endDateTime(Constants.instanceEndDateTime)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final Employee instanceEmployee = EmployeeMother.complete().build();

    public static final LocalDateTime instanceStartDateTime = LocalDateTime.now();

    public static final LocalDateTime instanceEndDateTime = LocalDateTime.now();

    public static final String instanceNote = "note";

  }

}
