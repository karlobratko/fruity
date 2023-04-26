package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateWorkEquipmentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.WorkEquipment;
import hr.algebra.fruity.repository.EquipmentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkEquipmentRequestDtoWithWorkAdapterToWorkEquipmentConverter implements Converter<CreateWorkEquipmentRequestDtoWithWorkAdapter, WorkEquipment> {

  private final EquipmentRepository equipmentRepository;

  @Override
  public WorkEquipment convert(@NonNull CreateWorkEquipmentRequestDtoWithWorkAdapter source) {
    return WorkEquipment.builder()
      .work(source.work())
      .equipment(equipmentRepository.findById(source.dto().equipmentFk()).orElseThrow(EntityNotFoundException::new))
      .costPerHour(source.dto().costPerHour())
      .note(source.dto().note())
      .build();
  }

}
