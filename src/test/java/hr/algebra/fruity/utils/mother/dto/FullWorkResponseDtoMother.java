package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.FullWorkResponseDto;
import hr.algebra.fruity.dto.response.FullWorkTypeResponseDto;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullWorkResponseDtoMother {

  public static FullWorkResponseDto.FullWorkResponseDtoBuilder complete() {
    return FullWorkResponseDto.builder()
      .id(Constants.instanceId)
      .startDateTime(Constants.instanceStartDateTime)
      .endDateTime(Constants.instanceEndDateTime)
      .finished(Constants.instanceFinished)
      .note(Constants.instanceNote)
      .type(Constants.instanceType);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final LocalDateTime instanceStartDateTime = LocalDateTime.now();

    public static final LocalDateTime instanceEndDateTime = LocalDateTime.now();

    public static final boolean instanceFinished = false;

    public static final String instanceNote = "note";

    public static final FullWorkTypeResponseDto instanceType = FullWorkTypeResponseDtoMother.complete().build();

  }

}
