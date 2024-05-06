package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.WorkAttachmentResponseDto;
import hr.algebra.fruity.model.WorkAttachment;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkAttachmentToWorkAttachmentResponseDtoConverter implements Converter<WorkAttachment, WorkAttachmentResponseDto> {

  private final AttachmentToAttachmentResponseDtoConverter attachmentConverter;

  @Override
  public WorkAttachmentResponseDto convert(@NonNull WorkAttachment source) {
    return new WorkAttachmentResponseDto(
      source.getWork().getId(),
      attachmentConverter.convert(source.getAttachment()),
      source.getCostPerHour(),
      source.getNote()
    );
  }

}
