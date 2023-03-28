package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.EquipmentResponseDto;
import hr.algebra.fruity.model.Equipment;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EquipmentToEquipmentResponseDtoConverter implements Converter<Equipment, EquipmentResponseDto> {

  @Override
  public EquipmentResponseDto convert(@NonNull Equipment source) {
    return new EquipmentResponseDto(
      source.getId(),
      source.getUuid(),
      source.getName(),
      source.getProductionYear(),
      source.getCostPerHour(),
      source.getPurchasePrice()
    );
  }

}
