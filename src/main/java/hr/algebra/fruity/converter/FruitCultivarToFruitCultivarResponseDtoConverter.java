package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FruitCultivarResponseDto;
import hr.algebra.fruity.model.FruitCultivar;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FruitCultivarToFruitCultivarResponseDtoConverter implements Converter<FruitCultivar, FruitCultivarResponseDto> {

  @Override
  public FruitCultivarResponseDto convert(@NonNull FruitCultivar source) {
    return new FruitCultivarResponseDto(
      source.getId(),
      source.getName(),
      source.getFruitSpecies().getName()
    );
  }

}
