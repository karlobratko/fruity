package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateWorkRequestDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.repository.WorkTypeRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkRequestDtoToWorkConverter implements Converter<CreateWorkRequestDto, Work> {

  private final CurrentRequestUserService currentRequestUserService;

  private final WorkTypeRepository workTypeRepository;

  @Override
  public Work convert(@NonNull CreateWorkRequestDto source) {
    return Work.builder()
      .user(currentRequestUserService.getUser())
      .startDateTime(source.startDateTime())
      .endDateTime(source.endDateTime())
      .note(source.note())
      .finished(Objects.nonNull(source.finished()) ? source.finished() : false)
      .type(workTypeRepository.findById(source.typeFk()).orElseThrow(EntityNotFoundException::new))
      .build();
  }

}
