package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkRequestDto;
import hr.algebra.fruity.model.WorkType;
import hr.algebra.fruity.utils.mother.model.WorkTypeMother;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinedCreateWorkRequestDtoMother {

  public static JoinedCreateWorkRequestDto.JoinedCreateWorkRequestDtoBuilder complete() {
    return JoinedCreateWorkRequestDto.builder()
      .startDateTime(Constants.instanceStartDateTime)
      .endDateTime(Constants.instanceEndDateTime)
      .finished(Constants.instanceFinished)
      .note(Constants.instanceNote)
      .type(Constants.instanceType);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final LocalDateTime instanceStartDateTime = LocalDateTime.now();

    public static final LocalDateTime instanceEndDateTime = LocalDateTime.now();

    public static final boolean instanceFinished = false;

    public static final String instanceNote = "note";

    public static final WorkType instanceType = WorkTypeMother.complete().build();

  }

}
