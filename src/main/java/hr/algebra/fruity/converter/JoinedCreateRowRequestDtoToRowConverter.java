package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRowRequestDto;
import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.service.CurrentRequestUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateRowRequestDtoToRowConverter implements Converter<JoinedCreateRowRequestDto, Row> {

  private final CurrentRequestUserService currentRequestUserService;

  @Override
  public Row convert(@NonNull JoinedCreateRowRequestDto source) {
    return Row.builder()
      .user(currentRequestUserService.getUser())
      .ordinal(source.ordinal())
      .rowCluster(source.rowCluster())
      .fruitCultivar(source.fruitCultivar())
      .numberOfSeedlings(source.numberOfSeedlings())
      .plantingYear(source.plantingYear())
      .build();
  }

}
