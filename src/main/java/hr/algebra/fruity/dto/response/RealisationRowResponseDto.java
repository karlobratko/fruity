package hr.algebra.fruity.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record RealisationRowResponseDto(
  Long realisationFk,
  FullRowResponseDto row,
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String realisationFk = "realisationFk";

    public static final String row = "row";

    public static final String note = "note";

  }

}