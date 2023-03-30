package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.UpdateAttachmentRequestDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateAttachmentRequestDtoMother {

  public static UpdateAttachmentRequestDto.UpdateAttachmentRequestDtoBuilder complete() {
    return UpdateAttachmentRequestDto.builder()
      .name(Constants.instanceName)
      .productionYear(Constants.instanceProductionYear)
      .costPerHour(Constants.instanceCostPerHour)
      .purchasePrice(Constants.instancePurchasePrice);
  }

  public static class Constants {

    public static final String instanceName = "name";

    public static final Integer instanceProductionYear = LocalDate.now().getYear();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ZERO;

    public static final BigDecimal instancePurchasePrice = BigDecimal.ZERO;

  }

}
