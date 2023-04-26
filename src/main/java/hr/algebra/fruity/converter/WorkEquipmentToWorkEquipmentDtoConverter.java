package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.WorkEquipmentResponseDto;
import hr.algebra.fruity.model.WorkEquipment;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkEquipmentToWorkEquipmentDtoConverter implements Converter<WorkEquipment, WorkEquipmentResponseDto> {

  private final EquipmentToEquipmentResponseDtoConverter equipmentConverter;

  @Override
  public WorkEquipmentResponseDto convert(@NonNull WorkEquipment source) {
    return new WorkEquipmentResponseDto(
      source.getWork().getId(),
      equipmentConverter.convert(source.getEquipment()),
      source.getCostPerHour(),
      source.getNote()
    );
  }

}
