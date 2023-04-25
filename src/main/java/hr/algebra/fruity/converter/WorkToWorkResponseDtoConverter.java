package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.WorkResponseDto;
import hr.algebra.fruity.model.Work;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WorkToWorkResponseDtoConverter implements Converter<Work, WorkResponseDto> {

  @Override
  public WorkResponseDto convert(@NonNull Work source) {
    return new WorkResponseDto(
      source.getId(),
      source.getStartDateTime(),
      source.getEndDateTime(),
      source.isFinished(),
      source.getType().getId()
    );
  }

}
