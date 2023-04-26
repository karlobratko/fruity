package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record CreateWorkRowRequestDto(
  Long cadastralParcelFk,
  Long arcodeParcelFk,
  Long rowClusterFk,
  List<Long> rowFks,
  Long rowFk,
  @Size(max = 500, message = "Napomena mora biti duljine maksimalno 500 znakova.")
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String cadastralParcelFk = "cadastralParcelFk";

    public static final String arcodeParcelFk = "arcodeParcelFk";

    public static final String rowClusterFk = "rowClusterFk";

    public static final String rowFks = "rowFks";

    public static final String rowFk = "rowFk";

    public static final String note = "note";

  }

}
