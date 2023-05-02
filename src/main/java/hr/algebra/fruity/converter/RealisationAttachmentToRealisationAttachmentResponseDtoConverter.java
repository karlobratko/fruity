package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.RealisationAttachmentResponseDto;
import hr.algebra.fruity.model.RealisationAttachment;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealisationAttachmentToRealisationAttachmentResponseDtoConverter implements Converter<RealisationAttachment, RealisationAttachmentResponseDto> {

  private final AttachmentToAttachmentResponseDtoConverter attachmentConverter;

  @Override
  public RealisationAttachmentResponseDto convert(@NonNull RealisationAttachment source) {
    return new RealisationAttachmentResponseDto(
      source.getRealisation().getId(),
      attachmentConverter.convert(source.getAttachment()),
      source.getCostPerHour(),
      source.getNote()
    );
  }

}
