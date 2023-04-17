package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateRowRequestDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.repository.FruitCultivarRepository;
import hr.algebra.fruity.repository.RowClusterRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRowRequestDtoToRowConverter implements Converter<CreateRowRequestDto, Row> {

  private final CurrentRequestUserService currentRequestUserService;

  private final RowClusterRepository rowClusterRepository;

  private final FruitCultivarRepository fruitCultivarRepository;

  @Override
  public Row convert(@NonNull CreateRowRequestDto source) {
    return Row.builder()
      .user(currentRequestUserService.getUser())
      .ordinal(source.ordinal())
      .rowCluster(rowClusterRepository.findById(source.rowClusterFk()).orElseThrow(EntityNotFoundException::new))
      .fruitCultivar(fruitCultivarRepository.findById(source.fruitCultivarFk()).orElseThrow(EntityNotFoundException::new))
      .numberOfSeedlings(source.numberOfSeedlings())
      .plantingYear(source.plantingYear())
      .build();
  }

}
