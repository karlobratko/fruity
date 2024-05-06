package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullWorkAttachmentResponseDto;
import hr.algebra.fruity.model.WorkAttachment;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkAttachmentToFullWorkAttachmentResponseDtoConverter implements Converter<WorkAttachment, FullWorkAttachmentResponseDto> {

  private final WorkToWorkResponseDtoConverter workConverter;

  private final AttachmentToAttachmentResponseDtoConverter attachmentConverter;

  @Override
  public FullWorkAttachmentResponseDto convert(@NonNull WorkAttachment source) {
    return new FullWorkAttachmentResponseDto(
      workConverter.convert(source.getWork()),
      attachmentConverter.convert(source.getAttachment()),
      source.getCostPerHour(),
      source.getNote()
    );
  }

}
