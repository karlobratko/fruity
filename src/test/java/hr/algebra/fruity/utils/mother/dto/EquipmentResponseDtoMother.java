package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.EquipmentResponseDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EquipmentResponseDtoMother {

  public static EquipmentResponseDto.EquipmentResponseDtoBuilder complete() {
    return EquipmentResponseDto.builder()
      .id(Constants.instanceId)
      .name(Constants.instanceName)
      .productionYear(Constants.instanceProductionYear)
      .costPerHour(Constants.instanceCostPerHour)
      .purchasePrice(Constants.instancePurchasePrice);
  }

  public static class Constants {

    public static final Long instanceId = 1L;

    public static final String instanceName = "name";

    public static final Integer instanceProductionYear = LocalDate.now().getYear();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ZERO;

    public static final BigDecimal instancePurchasePrice = BigDecimal.ZERO;

  }

}