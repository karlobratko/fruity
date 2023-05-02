package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.HarvestedFruitClassResponseDto;
import hr.algebra.fruity.model.HarvestedFruitClass;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HarvestedFruitClassToHarvestedFruitClassResponseDtoConverter implements Converter<HarvestedFruitClass, HarvestedFruitClassResponseDto> {

  @Override
  public HarvestedFruitClassResponseDto convert(@NonNull HarvestedFruitClass source) {
    return new HarvestedFruitClassResponseDto(
      source.getId(),
      source.getNumber(),
      source.getDisplayName()
    );
  }

}
