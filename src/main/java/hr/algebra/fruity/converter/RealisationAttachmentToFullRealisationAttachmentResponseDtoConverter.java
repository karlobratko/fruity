package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullRealisationAttachmentResponseDto;
import hr.algebra.fruity.model.RealisationAttachment;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealisationAttachmentToFullRealisationAttachmentResponseDtoConverter implements Converter<RealisationAttachment, FullRealisationAttachmentResponseDto> {

  private final RealisationToRealisationResponseDtoConverter realisationConverter;

  private final AttachmentToAttachmentResponseDtoConverter attachmentConverter;

  @Override
  public FullRealisationAttachmentResponseDto convert(@NonNull RealisationAttachment source) {
    return new FullRealisationAttachmentResponseDto(
      realisationConverter.convert(source.getRealisation()),
      attachmentConverter.convert(source.getAttachment()),
      source.getCostPerHour(),
      source.getNote()
    );
  }

}
