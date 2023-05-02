package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.CreateRealisationRowRequestDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateRealisationRowRequestDtoMother {

  public static CreateRealisationRowRequestDto.CreateRealisationRowRequestDtoBuilder complete() {
    return CreateRealisationRowRequestDto.builder()
      .cadastralParcelFk(Constants.instanceCadastralParcelFk)
      .arcodeParcelFk(Constants.instanceArcodeParcelFk)
      .rowClusterFk(Constants.instanceRowClusterFk)
      .rowFks(Constants.instanceRowFks)
      .rowFk(Constants.instanceRowFk)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceCadastralParcelFk = 1L;

    public static final Long instanceArcodeParcelFk = 1L;

    public static final Long instanceRowClusterFk = 1L;

    public static final List<Long> instanceRowFks = List.of(1L);

    public static final Long instanceRowFk = 1L;

    public static final String instanceNote = "note";

  }

}