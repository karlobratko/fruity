package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.CreateWorkRowRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateWorkRowRequestDtoMother {

  public static CreateWorkRowRequestDto.CreateWorkRowRequestDtoBuilder complete() {
    return CreateWorkRowRequestDto.builder()
      .rowFk(Constants.instanceRowFk)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceRowFk = 1L;

    public static final String instanceNote = "note";

  }

}
