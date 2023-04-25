package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullRowResponseDto;
import hr.algebra.fruity.model.Row;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RowToFullRowResponseDtoConverter implements Converter<Row, FullRowResponseDto> {

  private final RowClusterToRowClusterResponseDtoConverter rowClusterConverter;

  private final FruitCultivarToFruitCultivarResponseDtoConverter fruitCultivarConverter;

  @Override
  public FullRowResponseDto convert(@NonNull Row source) {
    return new FullRowResponseDto(
      source.getId(),
      source.getOrdinal(),
      rowClusterConverter.convert(source.getRowCluster()),
      source.getNumberOfSeedlings(),
      fruitCultivarConverter.convert(source.getFruitCultivar()),
      source.getPlantingYear()
    );
  }

}
