package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.FruitCultivarResponseDto;
import hr.algebra.fruity.model.FruitCultivar;
import java.util.List;

public interface FruitCultivarService {

  List<FruitCultivarResponseDto> getAllFruitCultivars();

  FruitCultivarResponseDto getFruitCultivarById(Integer id);

  FruitCultivar getById(Integer id);

}
