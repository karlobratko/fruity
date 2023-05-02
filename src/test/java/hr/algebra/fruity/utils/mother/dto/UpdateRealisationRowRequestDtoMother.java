package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.UpdateRealisationRowRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRealisationRowRequestDtoMother {

  public static UpdateRealisationRowRequestDto.UpdateRealisationRowRequestDtoBuilder complete() {
    return UpdateRealisationRowRequestDto.builder()
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String instanceNote = "note";

  }

}
