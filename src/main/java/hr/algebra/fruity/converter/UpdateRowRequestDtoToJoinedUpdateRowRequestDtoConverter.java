package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.UpdateRowRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedUpdateRowRequestDto;
import hr.algebra.fruity.service.FruitCultivarService;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateRowRequestDtoToJoinedUpdateRowRequestDtoConverter implements Converter<UpdateRowRequestDto, JoinedUpdateRowRequestDto> {

  private final FruitCultivarService fruitCultivarService;

  @Override
  public JoinedUpdateRowRequestDto convert(@NonNull UpdateRowRequestDto source) {
    return new JoinedUpdateRowRequestDto(
      source.ordinal(),
      source.numberOfSeedlings(),
      Objects.nonNull(source.fruitCultivarFk()) ? fruitCultivarService.getById(source.fruitCultivarFk()) : null,
      source.plantingYear()
    );
  }

}
