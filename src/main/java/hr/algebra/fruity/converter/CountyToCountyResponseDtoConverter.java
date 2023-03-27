package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.CountyResponseDto;
import hr.algebra.fruity.model.County;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CountyToCountyResponseDtoConverter implements Converter<County, CountyResponseDto> {

  @Override
  public CountyResponseDto convert(@NonNull County source) {
    return new CountyResponseDto(
      source.getId(),
      source.getUuid(),
      source.getAbbreviation(),
      source.getName()
    );
  }

}
