package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.FullRealisationRowResponseDto;
import hr.algebra.fruity.dto.response.RealisationResponseDto;
import hr.algebra.fruity.dto.response.RowResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullRealisationRowResponseDtoMother {

  public static FullRealisationRowResponseDto.FullRealisationRowResponseDtoBuilder complete() {
    return FullRealisationRowResponseDto.builder()
      .realisation(Constants.instanceRealisation)
      .row(Constants.instanceRow)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final RealisationResponseDto instanceRealisation = RealisationResponseDtoMother.complete().build();

    public static final RowResponseDto instanceRow = RowResponseDtoMother.complete().build();

    public static final String instanceNote = "note";

  }

}