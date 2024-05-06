package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkAttachmentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.model.WorkAttachment;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JoinedCreateWorkAttachmentRequestDtoWithWorkAdapterToWorkAttachmentConverter implements Converter<JoinedCreateWorkAttachmentRequestDtoWithWorkAdapter, WorkAttachment> {

  @Override
  public WorkAttachment convert(@NonNull JoinedCreateWorkAttachmentRequestDtoWithWorkAdapter source) {
    return WorkAttachment.builder()
      .work(source.work())
      .attachment(source.dto().attachment())
      .costPerHour(source.dto().costPerHour())
      .note(source.dto().note())
      .build();
  }

}
