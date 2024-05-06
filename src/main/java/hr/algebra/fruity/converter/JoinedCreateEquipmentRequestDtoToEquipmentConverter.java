package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.joined.JoinedCreateEquipmentRequestDto;
import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.service.CurrentRequestUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateEquipmentRequestDtoToEquipmentConverter implements Converter<JoinedCreateEquipmentRequestDto, Equipment> {

  private final CurrentRequestUserService currentRequestUserService;

  @Override
  public Equipment convert(@NonNull JoinedCreateEquipmentRequestDto source) {
    return Equipment.builder()
      .user(currentRequestUserService.getUser())
      .name(source.name())
      .productionYear(source.productionYear())
      .costPerHour(source.costPerHour())
      .purchasePrice(source.purchasePrice())
      .attachments(source.compatibleAttachments())
      .build();
  }

}
