package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullWorkEquipmentResponseDto;
import hr.algebra.fruity.model.WorkEquipment;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkEquipmentToFullWorkEquipmentResponseDtoConverter implements Converter<WorkEquipment, FullWorkEquipmentResponseDto> {

  private final WorkToWorkResponseDtoConverter workConverter;

  private final EquipmentToEquipmentResponseDtoConverter equipmentConverter;

  @Override
  public FullWorkEquipmentResponseDto convert(@NonNull WorkEquipment source) {
    return new FullWorkEquipmentResponseDto(
      workConverter.convert(source.getWork()),
      equipmentConverter.convert(source.getEquipment()),
      source.getCostPerHour(),
      source.getNote()
    );
  }

}
