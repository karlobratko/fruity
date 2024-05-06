package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.WorkResponseDto;
import hr.algebra.fruity.dto.response.WorkTypeResponseDto;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkResponseDtoMother {

  public static WorkResponseDto.WorkResponseDtoBuilder complete() {
    return WorkResponseDto.builder()
      .id(Constants.instanceId)
      .startDateTime(Constants.instanceStartDateTime)
      .endDateTime(Constants.instanceEndDateTime)
      .finished(Constants.instanceFinished)
      .type(Constants.instanceType);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final LocalDateTime instanceStartDateTime = LocalDateTime.now();

    public static final LocalDateTime instanceEndDateTime = LocalDateTime.now();

    public static final boolean instanceFinished = false;

    public static final String instanceNote = "note";

    public static final WorkTypeResponseDto instanceType = WorkTypeResponseDtoMother.complete().build();

  }

}
