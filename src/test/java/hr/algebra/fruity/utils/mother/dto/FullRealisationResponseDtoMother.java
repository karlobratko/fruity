package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.EmployeeResponseDto;
import hr.algebra.fruity.dto.response.FullRealisationResponseDto;
import hr.algebra.fruity.dto.response.WorkResponseDto;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullRealisationResponseDtoMother {

  public static FullRealisationResponseDto.FullRealisationResponseDtoBuilder complete() {
    return FullRealisationResponseDto.builder()
      .id(Constants.instanceId)
      .work(Constants.instanceWork)
      .employee(Constants.instanceEmployee)
      .startDateTime(Constants.instanceStartDateTime)
      .endDateTime(Constants.instanceEndDateTime)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final WorkResponseDto instanceWork = WorkResponseDtoMother.complete().build();

    public static final EmployeeResponseDto instanceEmployee = EmployeeResponseDtoMother.complete().build();

    public static final LocalDateTime instanceStartDateTime = LocalDateTime.now();

    public static final LocalDateTime instanceEndDateTime = LocalDateTime.now();

    public static final String instanceNote = "note";

  }

}
