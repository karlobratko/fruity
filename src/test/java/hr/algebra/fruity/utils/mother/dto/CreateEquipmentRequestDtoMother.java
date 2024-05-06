package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.CreateEquipmentRequestDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateEquipmentRequestDtoMother {

  public static CreateEquipmentRequestDto.CreateEquipmentRequestDtoBuilder complete() {
    return CreateEquipmentRequestDto.builder()
      .name(Constants.instanceName)
      .productionYear(Constants.instanceProductionYear)
      .costPerHour(Constants.instanceCostPerHour)
      .purchasePrice(Constants.instancePurchasePrice)
      .compatibleAttachmentFks(Constants.instanceCompatibleAttachmentFks);
  }

  public static class Constants {

    public static final String instanceName = "name";

    public static final Integer instanceProductionYear = LocalDate.now().getYear();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ZERO;

    public static final BigDecimal instancePurchasePrice = BigDecimal.ZERO;

    public static final List<Long> instanceCompatibleAttachmentFks = new ArrayList<>();

  }

}