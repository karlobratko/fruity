package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.UpdateRealisationRequestDto;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRealisationRequestDtoMother {

  public static UpdateRealisationRequestDto.UpdateRealisationRequestDtoBuilder complete() {
    return UpdateRealisationRequestDto.builder()
      .workFk(Constants.instanceWorkFk)
      .employeeFk(Constants.instanceEmployeeFk)
      .startDateTime(Constants.instanceStartDateTime)
      .endDateTime(Constants.instanceEndDateTime)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final Long instanceWorkFk = 1L;

    public static final Long instanceEmployeeFk = 1L;

    public static final LocalDateTime instanceStartDateTime = LocalDateTime.now();

    public static final LocalDateTime instanceEndDateTime = LocalDateTime.now();

    public static final String instanceNote = "note";

  }

}


