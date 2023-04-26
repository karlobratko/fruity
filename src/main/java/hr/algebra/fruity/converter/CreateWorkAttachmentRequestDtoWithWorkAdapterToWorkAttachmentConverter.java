package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateWorkAttachmentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.WorkAttachment;
import hr.algebra.fruity.repository.AttachmentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkAttachmentRequestDtoWithWorkAdapterToWorkAttachmentConverter implements Converter<CreateWorkAttachmentRequestDtoWithWorkAdapter, WorkAttachment> {

  private final AttachmentRepository attachmentRepository;

  @Override
  public WorkAttachment convert(@NonNull CreateWorkAttachmentRequestDtoWithWorkAdapter source) {
    return WorkAttachment.builder()
      .work(source.work())
      .attachment(attachmentRepository.findById(source.dto().attachmentFk()).orElseThrow(EntityNotFoundException::new))
      .costPerHour(source.dto().costPerHour())
      .note(source.dto().note())
      .build();
  }

}
