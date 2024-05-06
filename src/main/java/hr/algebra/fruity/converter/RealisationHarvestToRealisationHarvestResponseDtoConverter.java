package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.RealisationHarvestResponseDto;
import hr.algebra.fruity.model.RealisationHarvest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealisationHarvestToRealisationHarvestResponseDtoConverter implements Converter<RealisationHarvest, RealisationHarvestResponseDto> {

  private final FruitCultivarToFruitCultivarResponseDtoConverter fruitCultivarConverter;

  private final HarvestedFruitClassToHarvestedFruitClassResponseDtoConverter harvestedFruitClassConverter;

  private final UnitOfMeasureToUnitOfMeasureResponseDtoConverter unitOfMeasureConverter;

  @Override
  public RealisationHarvestResponseDto convert(@NonNull RealisationHarvest source) {
    return new RealisationHarvestResponseDto(
      source.getRealisation().getId(),
      fruitCultivarConverter.convert(source.getFruitCultivar()),
      harvestedFruitClassConverter.convert(source.getHarvestedFruitClass()),
      source.getQuantity(),
      unitOfMeasureConverter.convert(source.getUnitOfMeasure()),
      source.getNote()
    );
  }

}
