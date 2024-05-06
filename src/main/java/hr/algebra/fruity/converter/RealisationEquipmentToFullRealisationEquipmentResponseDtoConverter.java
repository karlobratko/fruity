package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullRealisationEquipmentResponseDto;
import hr.algebra.fruity.model.RealisationEquipment;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealisationEquipmentToFullRealisationEquipmentResponseDtoConverter implements Converter<RealisationEquipment, FullRealisationEquipmentResponseDto> {

  private final RealisationToRealisationResponseDtoConverter realisationConverter;

  private final EquipmentToEquipmentResponseDtoConverter equipmentConverter;

  @Override
  public FullRealisationEquipmentResponseDto convert(@NonNull RealisationEquipment source) {
    return new FullRealisationEquipmentResponseDto(
      realisationConverter.convert(source.getRealisation()),
      equipmentConverter.convert(source.getEquipment()),
      source.getCostPerHour(),
      source.getNote()
    );
  }

}
