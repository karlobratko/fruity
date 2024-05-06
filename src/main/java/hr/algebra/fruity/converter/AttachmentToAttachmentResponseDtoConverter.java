package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.AttachmentResponseDto;
import hr.algebra.fruity.model.Attachment;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AttachmentToAttachmentResponseDtoConverter implements Converter<Attachment, AttachmentResponseDto> {

  @Override
  public AttachmentResponseDto convert(@NonNull Attachment source) {
    return new AttachmentResponseDto(
      source.getId(),
      source.getName(),
      source.getProductionYear(),
      source.getCostPerHour(),
      source.getPurchasePrice()
    );
  }

}
