package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateWorkEquipmentRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkEquipmentRequestDto;
import hr.algebra.fruity.service.EquipmentService;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkEquipmentRequestDtoToJoinedCreateWorkEquipmentRequestDtoConverter implements Converter<CreateWorkEquipmentRequestDto, JoinedCreateWorkEquipmentRequestDto> {

  private final EquipmentService equipmentService;

  @Override
  public JoinedCreateWorkEquipmentRequestDto convert(@NonNull CreateWorkEquipmentRequestDto source) {
    val equipment = equipmentService.getById(source.equipmentFk());

    return new JoinedCreateWorkEquipmentRequestDto(
      equipment,
      Objects.requireNonNullElse(source.costPerHour(), equipment.getCostPerHour()),
      source.note()
    );
  }

}
