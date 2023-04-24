package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.WorkTypeResponseDto;
import hr.algebra.fruity.model.WorkType;
import java.util.List;

public interface WorkTypeService {

  List<WorkTypeResponseDto> getAllWorkTypes();

  WorkTypeResponseDto getWorkTypeById(Integer id);

  WorkType getById(Integer id);

}
