package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkRequestDto;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.service.CurrentRequestUserService;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateWorkRequestDtoToWorkConverter implements Converter<JoinedCreateWorkRequestDto, Work> {

  private final CurrentRequestUserService currentRequestUserService;

  @Override
  public Work convert(@NonNull JoinedCreateWorkRequestDto source) {
    return Work.builder()
      .user(currentRequestUserService.getUser())
      .startDateTime(source.startDateTime())
      .endDateTime(source.endDateTime())
      .note(source.note())
      .finished(Objects.nonNull(source.finished()) ? source.finished() : false)
      .type(source.type())
      .build();
  }

}
