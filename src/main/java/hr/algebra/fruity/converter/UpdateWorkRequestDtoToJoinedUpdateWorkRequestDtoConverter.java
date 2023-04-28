package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.UpdateWorkRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedUpdateWorkRequestDto;
import hr.algebra.fruity.service.WorkTypeService;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateWorkRequestDtoToJoinedUpdateWorkRequestDtoConverter implements Converter<UpdateWorkRequestDto, JoinedUpdateWorkRequestDto> {

  private final WorkTypeService workTypeService;

  @Override
  public JoinedUpdateWorkRequestDto convert(@NonNull UpdateWorkRequestDto source) {
    return new JoinedUpdateWorkRequestDto(
      source.startDateTime(),
      source.endDateTime(),
      Objects.nonNull(source.finished()) ? source.finished() : false,
      source.note(),
      Objects.nonNull(source.typeFk()) ? workTypeService.getById(source.typeFk()) : null
    );
  }

}
