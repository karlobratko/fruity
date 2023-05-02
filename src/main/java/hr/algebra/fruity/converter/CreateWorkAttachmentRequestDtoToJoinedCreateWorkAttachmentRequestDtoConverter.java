package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateWorkAttachmentRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkAttachmentRequestDto;
import hr.algebra.fruity.service.AttachmentService;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkAttachmentRequestDtoToJoinedCreateWorkAttachmentRequestDtoConverter implements Converter<CreateWorkAttachmentRequestDto, JoinedCreateWorkAttachmentRequestDto> {

  private final AttachmentService attachmentService;

  @Override
  public JoinedCreateWorkAttachmentRequestDto convert(@NonNull CreateWorkAttachmentRequestDto source) {
    val attachment = attachmentService.getById(source.attachmentFk());

    return new JoinedCreateWorkAttachmentRequestDto(
      attachment,
      Objects.requireNonNullElse(source.costPerHour(), attachment.getCostPerHour()),
      source.note()
    );
  }

}
