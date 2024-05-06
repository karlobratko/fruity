package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationRequestDto;
import hr.algebra.fruity.model.Realisation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateRealisationRequestDtoToRealisationConverter implements Converter<JoinedCreateRealisationRequestDto, Realisation> {

  @Override
  public Realisation convert(@NonNull JoinedCreateRealisationRequestDto source) {
    return Realisation.builder()
      .work(source.work())
      .employee(source.employee())
      .startDateTime(source.startDateTime())
      .endDateTime(source.endDateTime())
      .note(source.note())
      .build();
  }

}
