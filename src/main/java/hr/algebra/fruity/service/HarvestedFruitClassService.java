package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.HarvestedFruitClassResponseDto;
import hr.algebra.fruity.model.HarvestedFruitClass;
import java.util.List;

public interface HarvestedFruitClassService {

  List<HarvestedFruitClassResponseDto> getAllHarvestedFruitClasses();

  HarvestedFruitClassResponseDto getHarvestedFruitClassById(Integer id);

  HarvestedFruitClass getById(Integer id);

}
