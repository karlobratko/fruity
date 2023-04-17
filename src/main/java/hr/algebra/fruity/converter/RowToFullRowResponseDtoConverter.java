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

  private final RowClusterToRowClusterResponseDtoConverter rowClusterToRowClusterResponseDtoConverter;

  private final FruitCultivarToFruitCultivarResponseDtoConverter fruitCultivarToFruitCultivarResponseDtoConverter;

  @Override
  public FullRowResponseDto convert(@NonNull Row source) {
    return new FullRowResponseDto(
      source.getId(),
      source.getOrdinal(),
      rowClusterToRowClusterResponseDtoConverter.convert(source.getRowCluster()),
      source.getNumberOfSeedlings(),
      fruitCultivarToFruitCultivarResponseDtoConverter.convert(source.getFruitCultivar()),
      source.getPlantingYear()
    );
  }

}
