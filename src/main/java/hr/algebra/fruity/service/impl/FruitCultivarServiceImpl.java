package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.FruitCultivarToFruitCultivarResponseDtoConverter;
import hr.algebra.fruity.dto.response.FruitCultivarResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.FruitCultivar;
import hr.algebra.fruity.repository.FruitCultivarRepository;
import hr.algebra.fruity.service.FruitCultivarService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FruitCultivarServiceImpl implements FruitCultivarService {

  private final FruitCultivarToFruitCultivarResponseDtoConverter toFruitCultivarResponseDtoConverter;

  private final FruitCultivarRepository fruitCultivarRepository;

  @Override
  public List<FruitCultivarResponseDto> getAllFruitCultivars() {
    return fruitCultivarRepository.findAll().stream()
      .map(toFruitCultivarResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FruitCultivarResponseDto getFruitCultivarById(Integer id) {
    return toFruitCultivarResponseDtoConverter.convert(getById(id));
  }

  @Override
  public FruitCultivar getById(Integer id) {
    return fruitCultivarRepository.findById(id)
      .orElseThrow(EntityNotFoundException.supplier("Voćna vrsta"));
  }

}
