package hr.algebra.fruity.dto.request.joined;

import hr.algebra.fruity.model.Attachment;
import java.math.BigDecimal;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record JoinedUpdateEquipmentRequestDto(
  String name,
  Integer productionYear,
  BigDecimal costPerHour,
  BigDecimal purchasePrice,
  List<Attachment> compatibleAttachments
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String name = "name";

    public static final String productionYear = "productionYear";

    public static final String costPerHour = "costPerHour";

    public static final String purchasePrice = "purchasePrice";

    public static final String compatibleAttachments = "compatibleAttachments";

  }

}
