package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullWorkRowResponseDto;
import hr.algebra.fruity.model.WorkRow;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkRowToFullWorkRowResponseDtoConverter implements Converter<WorkRow, FullWorkRowResponseDto> {

  private final WorkToWorkResponseDtoConverter workConverter;

  private final RowToRowResponseDtoConverter rowConverter;

  @Override
  public FullWorkRowResponseDto convert(@NonNull WorkRow source) {
    return new FullWorkRowResponseDto(
      workConverter.convert(source.getWork()),
      rowConverter.convert(source.getRow()),
      source.getNote()
    );
  }

}
