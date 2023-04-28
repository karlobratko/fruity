package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.WorkTypeToFullWorkTypeResponseDtoConverter;
import hr.algebra.fruity.dto.response.FullWorkTypeResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.WorkType;
import hr.algebra.fruity.repository.WorkTypeRepository;
import hr.algebra.fruity.service.WorkTypeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkTypeServiceImpl implements WorkTypeService {

  private final WorkTypeToFullWorkTypeResponseDtoConverter toFullWorkTypeResponseDtoConverter;

  private final WorkTypeRepository workTypeRepository;

  @Override
  public List<FullWorkTypeResponseDto> getAllWorkTypes() {
    return workTypeRepository.findAll().stream()
      .map(toFullWorkTypeResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullWorkTypeResponseDto getWorkTypeById(Integer id) {
    return toFullWorkTypeResponseDtoConverter.convert(getById(id));
  }

  @Override
  public WorkType getById(Integer id) {
    return workTypeRepository.findById(id)
      .orElseThrow(EntityNotFoundException.supplier("Tip rada"));
  }

}
