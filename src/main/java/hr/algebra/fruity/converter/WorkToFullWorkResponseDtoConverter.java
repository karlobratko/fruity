package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullWorkResponseDto;
import hr.algebra.fruity.model.Work;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkToFullWorkResponseDtoConverter implements Converter<Work, FullWorkResponseDto> {

  private final WorkTypeToWorkTypeResponseDtoConverter workTypeToWorkTypeResponseDtoConverter;

  @Override
  public FullWorkResponseDto convert(@NonNull Work source) {
    return new FullWorkResponseDto(
      source.getId(),
      source.getStartDateTime(),
      source.getEndDateTime(),
      source.isFinished(),
      workTypeToWorkTypeResponseDtoConverter.convert(source.getType())
    );
  }

}
