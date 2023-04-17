package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.RowResponseDto;
import hr.algebra.fruity.model.Row;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RowToRowResponseDtoConverter implements Converter<Row, RowResponseDto> {

  @Override
  public RowResponseDto convert(@NonNull Row source) {
    return new RowResponseDto(
      source.getId(),
      source.getOrdinal(),
      source.getRowCluster().getId(),
      source.getNumberOfSeedlings(),
      source.getFruitCultivar().getId(),
      source.getPlantingYear()
    );
  }

}
