package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.RealisationEquipmentResponseDto;
import hr.algebra.fruity.model.RealisationEquipment;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealisationEquipmentToRealisationEquipmentResponseDtoConverter implements Converter<RealisationEquipment, RealisationEquipmentResponseDto> {

  private final EquipmentToEquipmentResponseDtoConverter equipmentConverter;

  @Override
  public RealisationEquipmentResponseDto convert(@NonNull RealisationEquipment source) {
    return new RealisationEquipmentResponseDto(
      source.getRealisation().getId(),
      equipmentConverter.convert(source.getEquipment()),
      source.getCostPerHour(),
      source.getNote()
    );
  }

}
