package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.UpdateWorkRequestDto;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateWorkRequestDtoMother {

  public static UpdateWorkRequestDto.UpdateWorkRequestDtoBuilder complete() {
    return UpdateWorkRequestDto.builder()
      .startDateTime(Constants.instanceStartDateTime)
      .endDateTime(Constants.instanceEndDateTime)
      .finished(Constants.instanceFinished)
      .note(Constants.instanceNote)
      .typeFk(Constants.instanceTypeFk);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final LocalDateTime instanceStartDateTime = LocalDateTime.now();

    public static final LocalDateTime instanceEndDateTime = LocalDateTime.now();

    public static final boolean instanceFinished = false;

    public static final String instanceNote = "note";

    public static final Integer instanceTypeFk = 1;

  }

}
