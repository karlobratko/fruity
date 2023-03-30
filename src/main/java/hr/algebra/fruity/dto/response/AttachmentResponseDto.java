package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record AttachmentResponseDto(
  Long id,
  UUID uuid,
  String name,
  Integer productionYear,
  BigDecimal costPerHour,
  BigDecimal purchasePrice
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String name = "name";

    public static final String productionYear = "productionYear";

    public static final String costPerHour = "costPerHour";

    public static final String purchasePrice = "purchasePrice";

  }
  
}
