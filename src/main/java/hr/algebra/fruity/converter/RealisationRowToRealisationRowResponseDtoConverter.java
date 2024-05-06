package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.RealisationRowResponseDto;
import hr.algebra.fruity.model.RealisationRow;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealisationRowToRealisationRowResponseDtoConverter implements Converter<RealisationRow, RealisationRowResponseDto> {

  private final RowToFullRowResponseDtoConverter rowConverter;

  @Override
  public RealisationRowResponseDto convert(@NonNull RealisationRow source) {
    return new RealisationRowResponseDto(
      source.getRealisation().getId(),
      rowConverter.convert(source.getRow()),
      source.getNote()
    );
  }

}
