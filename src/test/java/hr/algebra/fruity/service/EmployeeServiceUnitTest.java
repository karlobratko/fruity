package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.FullEmployeeResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.exception.ManagerEmployeeDeleteException;
import hr.algebra.fruity.mapper.EmployeeMapper;
import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.codebook.EmployeeRoles;
import hr.algebra.fruity.repository.EmployeeRepository;
import hr.algebra.fruity.service.impl.CurrentUserEmployeeService;
import hr.algebra.fruity.utils.mother.dto.CreateEmployeeRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullEmployeeResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateEmployeeRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.EmployeeMother;
import hr.algebra.fruity.utils.mother.model.EmployeeRoleMother;
import hr.algebra.fruity.utils.mother.model.MobileTokenMother;
import hr.algebra.fruity.utils.mother.model.RefreshTokenMother;
import hr.algebra.fruity.utils.mother.model.UserMother;
import hr.algebra.fruity.validator.CreateEmployeeRequestDtoValidator;
import hr.algebra.fruity.validator.EmployeeWithUpdateEmployeeRequestDtoValidator;
import java.util.Optional;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import static com.googlecode.catchexception.apis.BDDCatchException.caughtException;
import static com.googlecode.catchexception.apis.BDDCatchException.when;
import static org.assertj.core.api.BDDAssertions.and;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeService Unit Test")
@SuppressWarnings("static-access")
public class EmployeeServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserEmployeeService employeeService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private CreateEmployeeRequestDtoValidator createEmployeeRequestDtoValidator;

  @Mock
  private EmployeeWithUpdateEmployeeRequestDtoValidator employeeWithUpdateEmployeeRequestDtoValidator;

  @Mock
  private EmployeeMapper employeeMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private EmployeeRepository employeeRepository;

  @Mock
  private RefreshTokenService refreshTokenService;

  @Mock
  private MobileTokenService mobileTokenService;

  @Mock
  private EmployeeRoleService employeeRoleService;

  @Nested
  @DisplayName("WHEN getEmployeeById is called")
  public class WHEN_getEmployeeById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN EmployeeResponseDto is returned")
    public void GIVEN_id_THEN_EmployeeResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      val employee = EmployeeMother.complete().build();
      given(employeeRepository.findById(same(id))).willReturn(Optional.of(employee));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = employee.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... ConversionService successfully converts from Employee to FullEmployeeResponseDto
      val expectedResponseDto = FullEmployeeResponseDtoMother.complete().build();
      given(conversionService.convert(same(employee), same(FullEmployeeResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... getEmployeeById is called
      val responseDto = employeeService.getEmployeeById(id);

      // THEN
      // ... FullEmployeeResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createEmployee is called")
  public class WHEN_createEmployee {

    @Test
    @DisplayName("GIVEN CreateEmployeeRequestDto " +
      "... THEN EmployeeResponseDto")
    public void GIVEN_CreateEmployeeRequestDto_THEN_EmployeeResponseDto() {
      // GIVEN
      // ... CreateEmployeeRequestDto
      val requestDto = CreateEmployeeRequestDtoMother.complete().build();
      // CreateEmployeeRequestDtoValidator successfully validates CreateEmployeeRequestDto
      willDoNothing().given(createEmployeeRequestDtoValidator).validate(same(requestDto));
      // ... ConversionService successfully converts from CreateEmployeeRequestDto to Employee
      val employee = EmployeeMother.complete().build();
      given(conversionService.convert(same(requestDto), same(Employee.class))).willReturn(employee);
      // ... RefreshTokenService successfully creates RefreshToken
      val refreshToken = RefreshTokenMother.complete().build();
      given(refreshTokenService.createRefreshToken()).willReturn(refreshToken);
      // ... MobileTokenService successfully creates RefreshToken
      val mobileToken = MobileTokenMother.complete().build();
      given(mobileTokenService.createMobileToken()).willReturn(mobileToken);
      // ... EmployeeRepository will successfully save Employee
      given(employeeRepository.save(same(employee))).willReturn(employee);
      // ... ConversionService successfully converts from Employee to FullEmployeeResponseDto
      val expectedResponseDto = FullEmployeeResponseDtoMother.complete().build();
      given(conversionService.convert(same(employee), same(FullEmployeeResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... createEmployee is called
      val responseDto = employeeService.createEmployee(requestDto);

      // THEN
      // ... EmployeeResponseDto
      then(employeeRepository).should(times(1)).save(same(employee));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateEmployeeById is called")
  public class WHEN_updateEmployeeById {

    @Test
    @DisplayName("GIVEN id and UpdateEmployeeRequestDto " +
      "... THEN EmployeeResponseDto is returned")
    public void GIVEN_idAndUpdateEmployeeRequestDto_THEN_EmployeeResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      val employee = EmployeeMother.complete().build();
      given(employeeRepository.findById(same(id))).willReturn(Optional.of(employee));
      // ... UpdateEmployeeRequestDto
      val requestDto = UpdateEmployeeRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = employee.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... EmployeeWithUpdateEmployeeRequestDtoValidator successfully validates Employee with UpdateEmployeeRequestDto
      willDoNothing().given(employeeWithUpdateEmployeeRequestDtoValidator).validate(same(employee), same(requestDto));
      // ... EmployeeMapper successfully partially updates Employee with UpdateEmployeeRequestDto
      given(employeeMapper.partialUpdate(same(employee), same(requestDto))).willReturn(employee);
      // ... EmployeeRepository successfully saves Employee
      given(employeeRepository.save(same(employee))).willReturn(employee);
      // ... ConversionService successfully converts from Employee to FullEmployeeResponseDto
      val expectedResponseDto = FullEmployeeResponseDtoMother.complete().build();
      given(conversionService.convert(same(employee), same(FullEmployeeResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateEmployeeById is called
      val responseDto = employeeService.updateEmployeeById(id, requestDto);

      // THEN
      // ... EmployeeResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteEmployeeById is called")
  public class WHEN_deleteEmployeeById {

    @Test
    @DisplayName("GIVEN id and manager employee " +
      "... THEN ManagerEmployeeDeleteException is thrown")
    public void GIVEN_idAndManager_THEN_ManagerEmployeeDeleteException() {
      // GIVEN
      // ... id
      val id = 1L;
      val employee = EmployeeMother.complete().build();
      given(employeeRepository.findById(same(id))).willReturn(Optional.of(employee));
      // ... CurrentUserService's logged-in User is equal to Employee User
      val loggedInUser = employee.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... EmployeeRoleService will successfully return EmployeeRole
      val employeeRole = EmployeeRoleMother.complete().id(employee.getRole().getId()).build();
      given(employeeRoleService.getEmployeeRole(same(EmployeeRoles.ROLE_MANAGER))).willReturn(employeeRole);

      // WHEN
      // ... getEmployeeById is called
      when(() -> employeeService.deleteEmployeeById(id));

      // THEN
      // ... ManagerEmployeeDeleteException is thrown
      and.then(caughtException())
        .isInstanceOf(ManagerEmployeeDeleteException.class)
        .hasMessage(ManagerEmployeeDeleteException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN void")
    public void GIVEN_id_THEN_void() {
      // GIVEN
      // ... id
      val id = 1L;
      val employee = EmployeeMother.complete().build();
      given(employeeRepository.findById(same(id))).willReturn(Optional.of(employee));
      // ... CurrentUserService's logged-in User is not equal to Employee User
      val loggedInUser = employee.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... EmployeeRoleService will successfully return EmployeeRole
      val employeeRole = EmployeeRoleMother.complete().id(employee.getRole().getId() + 1).build();
      given(employeeRoleService.getEmployeeRole(same(EmployeeRoles.ROLE_MANAGER))).willReturn(employeeRole);
      // ... EmployeeRepository successfully deletes Employee
      willDoNothing().given(employeeRepository).delete(employee);

      // WHEN
      employeeService.deleteEmployeeById(id);

      // THEN
      // ... void
    }

  }

  @Nested
  @DisplayName("WHEN getById is called")
  public class WHEN_getById {

    @Test
    @DisplayName("GIVEN invalid id " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidId_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid id
      val id = 1L;
      given(employeeRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> employeeService.getById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(EntityNotFoundException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id and foreign logged-in User " +
      "... THEN ForeignUserDataAccessException is thrown")
    public void GIVEN_idAndForeignUser_THEN_ForeignUserDataAccessException() {
      // GIVEN
      // ... id
      val id = 1L;
      val employee = EmployeeMother.complete().build();
      given(employeeRepository.findById(same(id))).willReturn(Optional.of(employee));
      // ... CurrentUserService's logged-in User is not equal to Employee User
      val loggedInUser = UserMother.complete().id(employee.getUser().getId() + 1).build();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getById is called
      when(() -> employeeService.getById(id));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN Employee is returned")
    public void GIVEN_id_THEN_Employee() {
      // GIVEN
      // ... id
      val id = 1L;
      val expectedEmployee = EmployeeMother.complete().build();
      given(employeeRepository.findById(same(id))).willReturn(Optional.of(expectedEmployee));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = expectedEmployee.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getById is called
      val returnedEmployee = employeeService.getById(id);

      // THEN
      // ... EmployeeResponseDto is returned
      and.then(returnedEmployee)
        .isNotNull()
        .isEqualTo(expectedEmployee);
    }

  }

}
