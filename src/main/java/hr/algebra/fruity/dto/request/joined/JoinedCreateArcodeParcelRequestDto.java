package hr.algebra.fruity.dto.request.joined;

import hr.algebra.fruity.model.CadastralParcel;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record JoinedCreateArcodeParcelRequestDto(
  String name,
  CadastralParcel cadastralParcel,
  Integer arcode,
  BigDecimal surface
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String name = "name";

    public static final String cadastralParcel = "cadastralParcel";

    public static final String arcode = "arcode";

    public static final String surface = "surface";

  }

}
