package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.FullWorkRowResponseDto;
import hr.algebra.fruity.dto.response.RowResponseDto;
import hr.algebra.fruity.dto.response.WorkResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullWorkRowResponseDtoMother {

  public static FullWorkRowResponseDto.FullWorkRowResponseDtoBuilder complete() {
    return FullWorkRowResponseDto.builder()
      .work(Constants.instanceWork)
      .row(Constants.instanceRow)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final WorkResponseDto instanceWork = WorkResponseDtoMother.complete().build();

    public static final RowResponseDto instanceRow = RowResponseDtoMother.complete().build();

    public static final String instanceNote = "note";

  }

}