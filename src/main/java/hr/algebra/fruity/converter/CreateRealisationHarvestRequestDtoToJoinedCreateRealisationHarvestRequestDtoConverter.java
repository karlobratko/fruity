package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateRealisationHarvestRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationHarvestRequestDto;
import hr.algebra.fruity.service.FruitCultivarService;
import hr.algebra.fruity.service.HarvestedFruitClassService;
import hr.algebra.fruity.service.UnitOfMeasureService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRealisationHarvestRequestDtoToJoinedCreateRealisationHarvestRequestDtoConverter implements Converter<CreateRealisationHarvestRequestDto, JoinedCreateRealisationHarvestRequestDto> {

  private final FruitCultivarService fruitCultivarService;

  private final HarvestedFruitClassService harvestedFruitClassService;

  private final UnitOfMeasureService unitOfMeasureService;

  @Override
  public JoinedCreateRealisationHarvestRequestDto convert(@NonNull CreateRealisationHarvestRequestDto source) {
    return new JoinedCreateRealisationHarvestRequestDto(
      fruitCultivarService.getById(source.fruitCultivarFk()),
      harvestedFruitClassService.getById(source.fruitClassFk()),
      source.quantity(),
      unitOfMeasureService.getById(source.unitOfMeasureFk()),
      source.note()
    );
  }

}
