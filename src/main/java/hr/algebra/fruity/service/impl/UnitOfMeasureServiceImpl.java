package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.response.FullUnitOfMeasureResponseDto;
import hr.algebra.fruity.dto.response.UnitOfMeasureResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.UnitOfMeasure;
import hr.algebra.fruity.repository.UnitOfMeasureRepository;
import hr.algebra.fruity.service.UnitOfMeasureService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

  private final ConversionService conversionService;

  private final UnitOfMeasureRepository unitOfMeasureRepository;

  @Override
  public List<UnitOfMeasureResponseDto> getAllUnitsOfMeasure() {
    return unitOfMeasureRepository.findAll().stream()
      .map(unitOfMeasure -> conversionService.convert(unitOfMeasure, UnitOfMeasureResponseDto.class))
      .toList();
  }

  @Override
  public FullUnitOfMeasureResponseDto getUnitOfMeasureById(Integer id) {
    return conversionService.convert(getById(id), FullUnitOfMeasureResponseDto.class);
  }

  @Override
  public UnitOfMeasure getById(Integer id) {
    val unitOfMeasure = unitOfMeasureRepository.findById(id)
      .orElseThrow(EntityNotFoundException::new);

    return unitOfMeasure;
  }

}
