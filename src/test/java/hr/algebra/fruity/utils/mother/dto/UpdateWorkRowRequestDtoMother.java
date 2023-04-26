package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.UpdateWorkRowRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateWorkRowRequestDtoMother {

  public static UpdateWorkRowRequestDto.UpdateWorkRowRequestDtoBuilder complete() {
    return UpdateWorkRowRequestDto.builder()
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String instanceNote = "note";

  }

}
