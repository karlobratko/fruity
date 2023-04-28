package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateWorkEquipmentRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkEquipmentRequestDto;
import hr.algebra.fruity.service.EquipmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkEquipmentRequestDtoToJoinedCreateWorkEquipmentRequestDtoConverter implements Converter<CreateWorkEquipmentRequestDto, JoinedCreateWorkEquipmentRequestDto> {

  private final EquipmentService equipmentService;

  @Override
  public JoinedCreateWorkEquipmentRequestDto convert(@NonNull CreateWorkEquipmentRequestDto source) {
    return new JoinedCreateWorkEquipmentRequestDto(
      equipmentService.getById(source.equipmentFk()),
      source.costPerHour(),
      source.note()
    );
  }

}
