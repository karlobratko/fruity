package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.AttachmentResponseDto;
import hr.algebra.fruity.dto.response.FullEquipmentResponseDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullEquipmentResponseDtoMother {

  public static FullEquipmentResponseDto.FullEquipmentResponseDtoBuilder complete() {
    return FullEquipmentResponseDto.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .name(Constants.instanceName)
      .productionYear(Constants.instanceProductionYear)
      .costPerHour(Constants.instanceCostPerHour)
      .purchasePrice(Constants.instancePurchasePrice)
      .compatibleAttachments(Constants.instanceCompatibleAttachments);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final String instanceName = "name";

    public static final Integer instanceProductionYear = LocalDate.now().getYear();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ZERO;

    public static final BigDecimal instancePurchasePrice = BigDecimal.ZERO;

    public static final List<Long> instanceCompatibleAttachmentFks = new ArrayList<>();

    public static final List<AttachmentResponseDto> instanceCompatibleAttachments = new ArrayList<>();

  }

}