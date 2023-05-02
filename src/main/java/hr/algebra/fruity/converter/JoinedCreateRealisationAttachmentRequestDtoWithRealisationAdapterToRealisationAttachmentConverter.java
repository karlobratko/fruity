package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.model.RealisationAttachment;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterToRealisationAttachmentConverter implements Converter<JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapter, RealisationAttachment> {

  @Override
  public RealisationAttachment convert(@NonNull JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapter source) {
    return RealisationAttachment.builder()
      .realisation(source.realisation())
      .attachment(source.dto().attachment())
      .costPerHour(source.dto().costPerHour())
      .note(source.dto().note())
      .build();
  }

}
