package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.EmployeeResponseDto;
import hr.algebra.fruity.dto.response.RealisationResponseDto;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RealisationResponseDtoMother {

  public static RealisationResponseDto.RealisationResponseDtoBuilder complete() {
    return RealisationResponseDto.builder()
      .id(Constants.instanceId)
      .workFk(Constants.instanceWorkFk)
      .employee(Constants.instanceEmployee)
      .startDateTime(Constants.instanceStartDateTime)
      .endDateTime(Constants.instanceEndDateTime);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final Long instanceWorkFk = 1L;

    public static final EmployeeResponseDto instanceEmployee = EmployeeResponseDtoMother.complete().build();

    public static final LocalDateTime instanceStartDateTime = LocalDateTime.now();

    public static final LocalDateTime instanceEndDateTime = LocalDateTime.now();

    public static final String instanceNote = "note";

  }

}
