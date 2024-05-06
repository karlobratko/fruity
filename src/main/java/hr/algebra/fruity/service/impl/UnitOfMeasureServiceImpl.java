package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.UnitOfMeasureToFullUnitOfMeasureResponseDtoConverter;
import hr.algebra.fruity.converter.UnitOfMeasureToUnitOfMeasureResponseDtoConverter;
import hr.algebra.fruity.dto.response.FullUnitOfMeasureResponseDto;
import hr.algebra.fruity.dto.response.UnitOfMeasureResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.UnitOfMeasure;
import hr.algebra.fruity.repository.UnitOfMeasureRepository;
import hr.algebra.fruity.service.UnitOfMeasureService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

  private final UnitOfMeasureToUnitOfMeasureResponseDtoConverter toUnitOfMeasureResponseDtoConverter;

  private final UnitOfMeasureToFullUnitOfMeasureResponseDtoConverter toFullUnitOfMeasureResponseDtoConverter;

  private final UnitOfMeasureRepository unitOfMeasureRepository;

  @Override
  public List<UnitOfMeasureResponseDto> getAllUnitsOfMeasure() {
    return unitOfMeasureRepository.findAll().stream()
      .map(toUnitOfMeasureResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullUnitOfMeasureResponseDto getUnitOfMeasureById(Integer id) {
    return toFullUnitOfMeasureResponseDtoConverter.convert(getById(id));
  }

  @Override
  public UnitOfMeasure getById(Integer id) {
    return unitOfMeasureRepository.findById(id)
      .orElseThrow(EntityNotFoundException.supplier("Mjerna jedinica"));
  }

}
