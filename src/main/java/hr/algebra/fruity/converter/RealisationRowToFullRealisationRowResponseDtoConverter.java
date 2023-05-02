package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullRealisationRowResponseDto;
import hr.algebra.fruity.model.RealisationRow;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealisationRowToFullRealisationRowResponseDtoConverter implements Converter<RealisationRow, FullRealisationRowResponseDto> {

  private final RealisationToRealisationResponseDtoConverter realisationConverter;

  private final RowToRowResponseDtoConverter rowConverter;

  @Override
  public FullRealisationRowResponseDto convert(@NonNull RealisationRow source) {
    return new FullRealisationRowResponseDto(
      realisationConverter.convert(source.getRealisation()),
      rowConverter.convert(source.getRow()),
      source.getNote()
    );
  }

}
