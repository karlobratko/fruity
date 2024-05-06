package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.model.RealisationAttachment;
import hr.algebra.fruity.service.WorkAttachmentService;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterToRealisationAttachmentConverter implements Converter<JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapter, RealisationAttachment> {

  private final WorkAttachmentService workAttachmentService;

  @Override
  public RealisationAttachment convert(@NonNull JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapter source) {
    return RealisationAttachment.builder()
      .realisation(source.realisation())
      .attachment(source.dto().attachment())
      .costPerHour(Objects.requireNonNullElse(source.dto().costPerHour(), workAttachmentService.getByWorkIdAndAttachmentId(source.realisation().getWork().getId(), source.dto().attachment().getId()).getCostPerHour()))
      .note(source.dto().note())
      .build();
  }

}
