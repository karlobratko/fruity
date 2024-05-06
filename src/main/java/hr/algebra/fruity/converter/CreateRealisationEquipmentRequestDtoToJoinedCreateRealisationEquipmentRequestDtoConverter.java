package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateRealisationEquipmentRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationEquipmentRequestDto;
import hr.algebra.fruity.service.EquipmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRealisationEquipmentRequestDtoToJoinedCreateRealisationEquipmentRequestDtoConverter implements Converter<CreateRealisationEquipmentRequestDto, JoinedCreateRealisationEquipmentRequestDto> {

  private final EquipmentService equipmentService;

  @Override
  public JoinedCreateRealisationEquipmentRequestDto convert(@NonNull CreateRealisationEquipmentRequestDto source) {
    return new JoinedCreateRealisationEquipmentRequestDto(
      equipmentService.getById(source.equipmentFk()),
      source.costPerHour(),
      source.note()
    );
  }

}
