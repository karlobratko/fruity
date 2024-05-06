package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.HarvestedFruitClassToHarvestedFruitClassResponseDtoConverter;
import hr.algebra.fruity.dto.response.HarvestedFruitClassResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.HarvestedFruitClass;
import hr.algebra.fruity.repository.HarvestedFruitClassRepository;
import hr.algebra.fruity.service.HarvestedFruitClassService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HarvestedFruitClassServiceImpl implements HarvestedFruitClassService {

  private final HarvestedFruitClassToHarvestedFruitClassResponseDtoConverter toHarvestedFruitClassResponseDtoConverter;

  private final HarvestedFruitClassRepository agentRepository;

  @Override
  public List<HarvestedFruitClassResponseDto> getAllHarvestedFruitClasses() {
    return agentRepository.findAll().stream()
      .map(toHarvestedFruitClassResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public HarvestedFruitClassResponseDto getHarvestedFruitClassById(Integer id) {
    return toHarvestedFruitClassResponseDtoConverter.convert(getById(id));
  }

  @Override
  public HarvestedFruitClass getById(Integer id) {
    return agentRepository.findById(id)
      .orElseThrow(EntityNotFoundException.supplier("Klasa voća"));
  }

}
