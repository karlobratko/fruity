package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.WorkResponseDto;
import hr.algebra.fruity.model.Work;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkToWorkResponseDtoConverter implements Converter<Work, WorkResponseDto> {

  private final WorkTypeToWorkTypeResponseDtoConverter workTypeConverter;

  @Override
  public WorkResponseDto convert(@NonNull Work source) {
    return new WorkResponseDto(
      source.getId(),
      source.getStartDateTime(),
      source.getEndDateTime(),
      source.isFinished(),
      workTypeConverter.convert(source.getType())
    );
  }

}
