package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateWorkRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkRequestDto;
import hr.algebra.fruity.service.WorkTypeService;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkRequestDtoToJoinedCreateWorkRequestDtoConverter implements Converter<CreateWorkRequestDto, JoinedCreateWorkRequestDto> {

  private final WorkTypeService workTypeService;

  @Override
  public JoinedCreateWorkRequestDto convert(@NonNull CreateWorkRequestDto source) {
    return new JoinedCreateWorkRequestDto(
      source.startDateTime(),
      source.endDateTime(),
      Objects.nonNull(source.finished()) ? source.finished() : false,
      source.note(),
      workTypeService.getById(source.typeFk())
    );
  }

}
