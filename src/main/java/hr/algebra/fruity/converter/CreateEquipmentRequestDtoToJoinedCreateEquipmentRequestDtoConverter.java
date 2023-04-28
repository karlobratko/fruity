package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateEquipmentRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateEquipmentRequestDto;
import hr.algebra.fruity.service.AttachmentService;
import java.util.Collections;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateEquipmentRequestDtoToJoinedCreateEquipmentRequestDtoConverter implements Converter<CreateEquipmentRequestDto, JoinedCreateEquipmentRequestDto> {

  private final AttachmentService attachmentService;

  @Override
  public JoinedCreateEquipmentRequestDto convert(@NonNull CreateEquipmentRequestDto source) {
    return new JoinedCreateEquipmentRequestDto(
      source.name(),
      source.productionYear(),
      source.costPerHour(),
      source.purchasePrice(),
      Objects.nonNull(source.compatibleAttachmentFks()) ? attachmentService.getAllById(source.compatibleAttachmentFks()) : Collections.emptyList()
    );
  }

}
