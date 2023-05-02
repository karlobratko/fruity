package hr.algebra.fruity.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record FullRealisationRowResponseDto(
  RealisationResponseDto realisation,
  RowResponseDto row,
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String realisation = "realisation";

    public static final String row = "row";

    public static final String note = "note";

  }

}
