package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.RowResponseDto;
import hr.algebra.fruity.model.Row;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RowToRowResponseDtoConverter implements Converter<Row, RowResponseDto> {

  private final FruitCultivarToFruitCultivarResponseDtoConverter fruitCultivarToFruitCultivarResponseDtoConverter;

  @Override
  public RowResponseDto convert(@NonNull Row source) {
    return new RowResponseDto(
      source.getId(),
      source.getOrdinal(),
      source.getRowCluster().getId(),
      source.getNumberOfSeedlings(),
      fruitCultivarToFruitCultivarResponseDtoConverter.convert(source.getFruitCultivar()),
      source.getPlantingYear()
    );
  }

}
