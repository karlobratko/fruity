package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateWorkAttachmentRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkAttachmentRequestDto;
import hr.algebra.fruity.service.AttachmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkAttachmentRequestDtoToJoinedCreateWorkAttachmentRequestDtoConverter implements Converter<CreateWorkAttachmentRequestDto, JoinedCreateWorkAttachmentRequestDto> {

  private final AttachmentService attachmentService;

  @Override
  public JoinedCreateWorkAttachmentRequestDto convert(@NonNull CreateWorkAttachmentRequestDto source) {
    return new JoinedCreateWorkAttachmentRequestDto(
      attachmentService.getById(source.attachmentFk()),
      source.costPerHour(),
      source.note()
    );
  }

}
