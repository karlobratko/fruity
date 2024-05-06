package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.WorkRowResponseDto;
import hr.algebra.fruity.model.WorkRow;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkRowToWorkRowResponseDtoConverter implements Converter<WorkRow, WorkRowResponseDto> {

  private final RowToFullRowResponseDtoConverter rowConverter;

  @Override
  public WorkRowResponseDto convert(@NonNull WorkRow source) {
    return new WorkRowResponseDto(
      source.getWork().getId(),
      rowConverter.convert(source.getRow()),
      source.getNote()
    );
  }

}
