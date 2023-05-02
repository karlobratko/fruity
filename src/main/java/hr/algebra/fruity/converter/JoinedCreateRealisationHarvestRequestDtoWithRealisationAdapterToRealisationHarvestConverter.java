package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.model.RealisationHarvest;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapterToRealisationHarvestConverter implements Converter<JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapter, RealisationHarvest> {

  @Override
  public RealisationHarvest convert(@NonNull JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapter source) {
    return RealisationHarvest.builder()
      .realisation(source.realisation())
      .fruitCultivar(source.dto().fruitCultivar())
      .harvestedFruitClass(source.dto().fruitClass())
      .quantity(source.dto().quantity())
      .unitOfMeasure(source.dto().unitOfMeasure())
      .note(source.dto().note())
      .build();
  }

}
