package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateAttachmentRequestDto;
import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.service.CurrentRequestUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAttachmentRequestDtoToAttachmentConverter implements Converter<CreateAttachmentRequestDto, Attachment> {

  private final CurrentRequestUserService currentRequestUserService;

  @Override
  public Attachment convert(@NonNull CreateAttachmentRequestDto source) {
    return Attachment.builder()
      .user(currentRequestUserService.getUser())
      .name(source.name())
      .productionYear(source.productionYear())
      .costPerHour(source.costPerHour())
      .purchasePrice(source.purchasePrice()).build();
  }

}
