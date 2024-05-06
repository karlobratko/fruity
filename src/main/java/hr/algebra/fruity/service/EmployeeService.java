package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateEmployeeRequestDto;
import hr.algebra.fruity.dto.request.UpdateEmployeeRequestDto;
import hr.algebra.fruity.dto.response.EmployeeResponseDto;
import hr.algebra.fruity.dto.response.FullEmployeeResponseDto;
import hr.algebra.fruity.dto.response.WorkResponseDto;
import hr.algebra.fruity.model.Employee;
import java.util.List;

public interface EmployeeService {

  List<EmployeeResponseDto> getAllEmployees();

  FullEmployeeResponseDto getEmployeeById(Long id);

  FullEmployeeResponseDto createEmployee(CreateEmployeeRequestDto requestDto);

  FullEmployeeResponseDto updateEmployeeById(Long id, UpdateEmployeeRequestDto requestDto);

  void deleteEmployeeById(Long id);

  Employee getById(Long id);

  Employee getByIdIgnoreSoftDelete(Long id);

  List<WorkResponseDto> getAllAssignedWorks();

}
