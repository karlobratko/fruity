package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullEquipmentResponseDto;
import hr.algebra.fruity.model.Equipment;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquipmentToFullEquipmentResponseDtoConverter implements Converter<Equipment, FullEquipmentResponseDto> {

  private final AttachmentToAttachmentResponseDtoConverter attachmentToAttachmentResponseDtoConverter;

  @Override
  public FullEquipmentResponseDto convert(@NonNull Equipment source) {
    return new FullEquipmentResponseDto(
      source.getId(),
      source.getUuid(),
      source.getName(),
      source.getProductionYear(),
      source.getCostPerHour(),
      source.getPurchasePrice(),
      source.getAttachments().stream().map(attachmentToAttachmentResponseDtoConverter::convert).toList()
    );
  }

}
