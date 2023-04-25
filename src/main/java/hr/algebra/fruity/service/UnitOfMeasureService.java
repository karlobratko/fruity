package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.FullUnitOfMeasureResponseDto;
import hr.algebra.fruity.dto.response.UnitOfMeasureResponseDto;
import hr.algebra.fruity.model.UnitOfMeasure;
import java.util.List;

public interface UnitOfMeasureService {

  List<UnitOfMeasureResponseDto> getAllUnitsOfMeasure();

  FullUnitOfMeasureResponseDto getUnitOfMeasureById(Integer id);

  UnitOfMeasure getById(Integer id);

}
