package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateEquipmentRequestDto;
import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.repository.AttachmentRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateEquipmentRequestDtoToEquipmentConverter implements Converter<CreateEquipmentRequestDto, Equipment> {

  private final CurrentRequestUserService currentRequestUserService;

  private final AttachmentRepository attachmentRepository;

  @Override
  public Equipment convert(@NonNull CreateEquipmentRequestDto source) {
    val equipment = Equipment.builder()
      .user(currentRequestUserService.getUser())
      .name(source.name())
      .productionYear(source.productionYear())
      .costPerHour(source.costPerHour())
      .purchasePrice(source.purchasePrice());

    if (Objects.nonNull(source.compatibleAttachmentFks()))
      equipment.attachments(attachmentRepository.findAllById(source.compatibleAttachmentFks()));

    return equipment.build();
  }

}
