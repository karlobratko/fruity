package hr.algebra.fruity.dto.request.joined;

import hr.algebra.fruity.model.Row;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record JoinedCreateWorkRowRequestDto(
  Set<Row> rows,
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String cadastralParcelFk = "cadastralParcel";

    public static final String arcodeParcelFk = "arcodeParcel";

    public static final String rowCluster = "rowCluster";

    public static final String rows = "rows";

    public static final String row = "row";

    public static final String note = "note";

  }

}
