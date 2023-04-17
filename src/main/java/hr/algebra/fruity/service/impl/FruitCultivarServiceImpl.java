package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.response.FruitCultivarResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.FruitCultivar;
import hr.algebra.fruity.repository.FruitCultivarRepository;
import hr.algebra.fruity.service.FruitCultivarService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FruitCultivarServiceImpl implements FruitCultivarService {

  private final ConversionService conversionService;

  private final FruitCultivarRepository fruitCultivarRepository;

  @Override
  public List<FruitCultivarResponseDto> getAllFruitCultivars() {
    return fruitCultivarRepository.findAll().stream()
      .map(county -> conversionService.convert(county, FruitCultivarResponseDto.class))
      .toList();
  }

  @Override
  public FruitCultivarResponseDto getFruitCultivarById(Integer id) {
    return conversionService.convert(getById(id), FruitCultivarResponseDto.class);
  }

  @Override
  public FruitCultivar getById(Integer id) {
    val county = fruitCultivarRepository.findById(id)
      .orElseThrow(EntityNotFoundException::new);

    return county;
  }

}
