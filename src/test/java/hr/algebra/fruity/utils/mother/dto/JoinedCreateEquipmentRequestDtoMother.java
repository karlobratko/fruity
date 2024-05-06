package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.joined.JoinedCreateEquipmentRequestDto;
import hr.algebra.fruity.model.Attachment;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinedCreateEquipmentRequestDtoMother {

  public static JoinedCreateEquipmentRequestDto.JoinedCreateEquipmentRequestDtoBuilder complete() {
    return JoinedCreateEquipmentRequestDto.builder()
      .name(Constants.instanceName)
      .productionYear(Constants.instanceProductionYear)
      .costPerHour(Constants.instanceCostPerHour)
      .purchasePrice(Constants.instancePurchasePrice)
      .compatibleAttachments(Constants.instanceCompatibleAttachments);
  }

  public static class Constants {

    public static final String instanceName = "name";

    public static final Integer instanceProductionYear = LocalDate.now().getYear();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ZERO;

    public static final BigDecimal instancePurchasePrice = BigDecimal.ZERO;

    public static final List<Attachment> instanceCompatibleAttachments = Collections.emptyList();

  }

}