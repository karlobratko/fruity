package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullUnitOfMeasureResponseDto;
import hr.algebra.fruity.model.UnitOfMeasure;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnitOfMeasureToFullUnitOfMeasureResponseDtoConverter implements Converter<UnitOfMeasure, FullUnitOfMeasureResponseDto> {

  private final UnitOfMeasureToUnitOfMeasureResponseDtoConverter unitOfMeasureConverter;

  @Override
  public FullUnitOfMeasureResponseDto convert(@NonNull UnitOfMeasure source) {
    return new FullUnitOfMeasureResponseDto(
      source.getId(),
      source.getAbbreviation(),
      source.getName(),
      Objects.nonNull(source.getBase()) ? unitOfMeasureConverter.convert(source.getBase()) : null,
      source.getBaseMultiplier()
    );
  }

}
