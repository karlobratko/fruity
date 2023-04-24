package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.response.WorkTypeResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.WorkType;
import hr.algebra.fruity.repository.WorkTypeRepository;
import hr.algebra.fruity.service.WorkTypeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkTypeServiceImpl implements WorkTypeService {

  private final ConversionService conversionService;

  private final WorkTypeRepository workTypeRepository;

  @Override
  public List<WorkTypeResponseDto> getAllWorkTypes() {
    return workTypeRepository.findAll().stream()
      .map(workType -> conversionService.convert(workType, WorkTypeResponseDto.class))
      .toList();
  }

  @Override
  public WorkTypeResponseDto getWorkTypeById(Integer id) {
    return conversionService.convert(getById(id), WorkTypeResponseDto.class);
  }

  @Override
  public WorkType getById(Integer id) {
    val workType = workTypeRepository.findById(id)
      .orElseThrow(EntityNotFoundException::new);

    return workType;
  }

}
