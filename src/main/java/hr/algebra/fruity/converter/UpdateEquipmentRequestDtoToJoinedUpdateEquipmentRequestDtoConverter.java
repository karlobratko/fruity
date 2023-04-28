package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.UpdateEquipmentRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedUpdateEquipmentRequestDto;
import hr.algebra.fruity.service.AttachmentService;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateEquipmentRequestDtoToJoinedUpdateEquipmentRequestDtoConverter implements Converter<UpdateEquipmentRequestDto, JoinedUpdateEquipmentRequestDto> {

  private final AttachmentService attachmentService;

  @Override
  public JoinedUpdateEquipmentRequestDto convert(@NonNull UpdateEquipmentRequestDto source) {
    return new JoinedUpdateEquipmentRequestDto(
      source.name(),
      source.productionYear(),
      source.costPerHour(),
      source.purchasePrice(),
      Objects.nonNull(source.compatibleAttachmentFks()) ? attachmentService.getAllById(source.compatibleAttachmentFks()) : null
    );
  }

}
