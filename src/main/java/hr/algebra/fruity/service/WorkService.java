package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateWorkRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkRequestDto;
import hr.algebra.fruity.dto.response.FullWorkResponseDto;
import hr.algebra.fruity.dto.response.WorkResponseDto;
import hr.algebra.fruity.model.Work;
import java.util.List;

public interface WorkService {

  List<WorkResponseDto> getAllWorks();

  FullWorkResponseDto getWorkById(Long id);

  FullWorkResponseDto createWork(CreateWorkRequestDto requestDto);

  FullWorkResponseDto updateWorkById(Long id, UpdateWorkRequestDto requestDto);

  void deleteWorkById(Long id);

  Work getById(Long id);

}
