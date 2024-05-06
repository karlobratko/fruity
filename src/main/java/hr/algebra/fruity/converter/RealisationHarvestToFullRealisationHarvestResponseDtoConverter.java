package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullRealisationHarvestResponseDto;
import hr.algebra.fruity.model.RealisationHarvest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealisationHarvestToFullRealisationHarvestResponseDtoConverter implements Converter<RealisationHarvest, FullRealisationHarvestResponseDto> {

  private final RealisationToRealisationResponseDtoConverter realisationConverter;

  private final FruitCultivarToFruitCultivarResponseDtoConverter fruitCultivarConverter;

  private final HarvestedFruitClassToHarvestedFruitClassResponseDtoConverter harvestedFruitClassConverter;

  private final UnitOfMeasureToUnitOfMeasureResponseDtoConverter unitOfMeasureConverter;

  @Override
  public FullRealisationHarvestResponseDto convert(@NonNull RealisationHarvest source) {
    return new FullRealisationHarvestResponseDto(
      realisationConverter.convert(source.getRealisation()),
      fruitCultivarConverter.convert(source.getFruitCultivar()),
      harvestedFruitClassConverter.convert(source.getHarvestedFruitClass()),
      source.getQuantity(),
      unitOfMeasureConverter.convert(source.getUnitOfMeasure()),
      source.getNote()
    );
  }

}
