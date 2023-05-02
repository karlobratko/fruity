package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateRealisationAttachmentRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationAttachmentRequestDto;
import hr.algebra.fruity.service.AttachmentService;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRealisationAttachmentRequestDtoToJoinedCreateRealisationAttachmentRequestDtoConverter implements Converter<CreateRealisationAttachmentRequestDto, JoinedCreateRealisationAttachmentRequestDto> {

  private final AttachmentService attachmentService;

  @Override
  public JoinedCreateRealisationAttachmentRequestDto convert(@NonNull CreateRealisationAttachmentRequestDto source) {
    val attachment = attachmentService.getById(source.attachmentFk());

    return new JoinedCreateRealisationAttachmentRequestDto(
      attachment,
      Objects.requireNonNullElse(source.costPerHour(), attachment.getCostPerHour()),
      source.note()
    );
  }

}
