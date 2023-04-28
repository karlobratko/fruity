package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateRowRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateRowRequestDto;
import hr.algebra.fruity.service.FruitCultivarService;
import hr.algebra.fruity.service.RowClusterService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRowRequestDtoToJoinedCreateRowRequestDtoConverter implements Converter<CreateRowRequestDto, JoinedCreateRowRequestDto> {

  private final RowClusterService rowClusterService;

  private final FruitCultivarService fruitCultivarService;

  @Override
  public JoinedCreateRowRequestDto convert(@NonNull CreateRowRequestDto source) {
    return new JoinedCreateRowRequestDto(
      source.ordinal(),
      rowClusterService.getById(source.rowClusterFk()),
      source.numberOfSeedlings(),
      fruitCultivarService.getById(source.fruitCultivarFk()),
      source.plantingYear()
    );
  }

}
