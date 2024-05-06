package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.UnitOfMeasureResponseDto;
import hr.algebra.fruity.model.UnitOfMeasure;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureResponseDtoConverter implements Converter<UnitOfMeasure, UnitOfMeasureResponseDto> {

  @Override
  public UnitOfMeasureResponseDto convert(@NonNull UnitOfMeasure source) {
    return new UnitOfMeasureResponseDto(
      source.getId(),
      source.getAbbreviation(),
      source.getName()
    );
  }

}
