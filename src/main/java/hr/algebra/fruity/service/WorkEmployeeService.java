package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateWorkEmployeeRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkEmployeeRequestDto;
import hr.algebra.fruity.dto.response.FullWorkEmployeeResponseDto;
import hr.algebra.fruity.dto.response.WorkEmployeeResponseDto;
import hr.algebra.fruity.model.WorkEmployee;
import java.util.List;

public interface WorkEmployeeService {

  List<WorkEmployeeResponseDto> getAllWorkEmployeesByWorkId(Long workFk);

  FullWorkEmployeeResponseDto getWorkEmployeeByWorkIdAndEmployeeId(Long workFk, Long employeeId);

  FullWorkEmployeeResponseDto createWorkEmployeeForWorkId(Long workFk, CreateWorkEmployeeRequestDto requestDto);

  FullWorkEmployeeResponseDto updateWorkEmployeeByWorkIdAndEmployeeId(Long workFk, Long employeeId, UpdateWorkEmployeeRequestDto requestDto);

  void deleteWorkEmployeeByWorkIdAndEmployeeId(Long workFk, Long employeeId);

  WorkEmployee getByWorkIdAndEmployeeId(Long workFk, Long employeeId);

}
