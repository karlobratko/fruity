package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.request.CreateEmployeeRequestDto;
import hr.algebra.fruity.dto.request.UpdateEmployeeRequestDto;
import hr.algebra.fruity.dto.response.EmployeeResponseDto;
import hr.algebra.fruity.dto.response.FullEmployeeResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.exception.ManagerEmployeeDeleteException;
import hr.algebra.fruity.mapper.EmployeeMapper;
import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.codebook.EmployeeRoles;
import hr.algebra.fruity.repository.EmployeeRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.EmployeeRoleService;
import hr.algebra.fruity.service.EmployeeService;
import hr.algebra.fruity.service.MobileTokenService;
import hr.algebra.fruity.service.RefreshTokenService;
import hr.algebra.fruity.validator.CreateEmployeeRequestDtoValidator;
import hr.algebra.fruity.validator.EmployeeWithUpdateEmployeeRequestDtoValidator;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserEmployeeService implements EmployeeService {

  private final ConversionService conversionService;

  private final CreateEmployeeRequestDtoValidator createEmployeeRequestDtoValidator;

  private final EmployeeWithUpdateEmployeeRequestDtoValidator employeeWithUpdateEmployeeRequestDtoValidator;

  private final EmployeeMapper employeeMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final EmployeeRepository employeeRepository;

  private final RefreshTokenService refreshTokenService;

  private final MobileTokenService mobileTokenService;

  private final EmployeeRoleService employeeRoleService;

  @Override
  public List<EmployeeResponseDto> getAllEmployees() {
    return employeeRepository.findAllByUserId(currentRequestUserService.getUserId()).stream()
      .map(employee -> conversionService.convert(employee, EmployeeResponseDto.class))
      .toList();
  }

  @Override
  public FullEmployeeResponseDto getEmployeeById(Long id) {
    return conversionService.convert(getEmployee(id), FullEmployeeResponseDto.class);
  }

  @Override
  @Transactional
  public FullEmployeeResponseDto createEmployee(CreateEmployeeRequestDto requestDto) {
    createEmployeeRequestDtoValidator.validate(requestDto);

    val employee = Objects.requireNonNull(conversionService.convert(requestDto, Employee.class));
    employee.setRefreshToken(refreshTokenService.createRefreshToken());
    employee.setMobileToken(mobileTokenService.createMobileToken());
    employee.activate();

    return conversionService.convert(
      employeeRepository.save(Objects.requireNonNull(employee)),
      FullEmployeeResponseDto.class
    );
  }

  @Override
  @Transactional
  public FullEmployeeResponseDto updateEmployeeById(Long id, UpdateEmployeeRequestDto requestDto) {
    val employee = getEmployee(id);

    employeeWithUpdateEmployeeRequestDtoValidator.validate(employee, requestDto);

    return conversionService.convert(
      employeeRepository.save(
        employeeMapper.partialUpdate(employee, requestDto)
      ),
      FullEmployeeResponseDto.class
    );
  }

  @Override
  @Transactional
  public void deleteEmployeeById(Long id) {
    val employee = getEmployee(id);

    if (Objects.equals(employee.getRole(), employeeRoleService.getEmployeeRole(EmployeeRoles.ROLE_MANAGER)))
      throw new ManagerEmployeeDeleteException();

    employeeRepository.delete(employee);
  }

  private Employee getEmployee(Long id) {
    val employee = employeeRepository.findById(id)
      .orElseThrow(EntityNotFoundException::new);

    if (!Objects.equals(employee.getUser().getId(), currentRequestUserService.getUserId()))
      throw new ForeignUserDataAccessException();

    return employee;
  }

}
