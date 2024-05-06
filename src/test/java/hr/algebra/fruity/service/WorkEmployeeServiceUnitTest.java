package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.CreateWorkEmployeeRequestDtoToJoinedCreateWorkEmployeeRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateWorkEmployeeRequestDtoWithWorkAdapterToWorkEmployeeConverter;
import hr.algebra.fruity.converter.WorkEmployeeToFullWorkEmployeeResponseDtoConverter;
import hr.algebra.fruity.converter.WorkEmployeeToWorkEmployeeResponseDtoConverter;
import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkEmployeeRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.WorkEmployeeMapper;
import hr.algebra.fruity.repository.WorkEmployeeRepository;
import hr.algebra.fruity.service.impl.CurrentUserWorkEmployeeService;
import hr.algebra.fruity.utils.mother.dto.CreateWorkEmployeeRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullWorkEmployeeResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedCreateWorkEmployeeRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateWorkEmployeeRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.WorkEmployeeMother;
import hr.algebra.fruity.utils.mother.model.WorkMother;
import hr.algebra.fruity.validator.JoinedCreateWorkEmployeeRequestDtoWithWorkAdapterValidator;
import java.util.Optional;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.googlecode.catchexception.apis.BDDCatchException.caughtException;
import static com.googlecode.catchexception.apis.BDDCatchException.when;
import static org.assertj.core.api.BDDAssertions.and;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("WorkEmployee Service Unit Test")
@SuppressWarnings("static-access")
public class WorkEmployeeServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserWorkEmployeeService workEmployeeService;

  @Mock
  private WorkEmployeeToWorkEmployeeResponseDtoConverter toWorkEmployeeResponseDtoConverter;

  @Mock
  private WorkEmployeeToFullWorkEmployeeResponseDtoConverter toFullWorkEmployeeResponseDtoConverter;

  @Mock
  private CreateWorkEmployeeRequestDtoToJoinedCreateWorkEmployeeRequestDtoConverter toJoinedCreateWorkEmployeeRequestDtoConverter;

  @Mock
  private JoinedCreateWorkEmployeeRequestDtoWithWorkAdapterToWorkEmployeeConverter fromJoinedCreateWorkEmployeeRequestDtoWithWorkAdapterConverter;

  @Mock
  private JoinedCreateWorkEmployeeRequestDtoWithWorkAdapterValidator joinedCreateWorkEmployeeRequestDtoWithWorkAdapterValidator;

  @Mock
  private WorkEmployeeMapper workEmployeeMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private WorkEmployeeRepository workEmployeeRepository;

  @Mock
  private WorkService workService;

  @Nested
  @DisplayName("WHEN getWorkEmployeeByWorkIdAndEmployeeId is called")
  public class WHEN_getWorkEmployeeByWorkIdAndEmployeeId {

    @Test
    @DisplayName("GIVEN workId and employeeId " +
      "... THEN WorkEmployeeResponseDto is returned")
    public void GIVEN_ids_THEN_WorkEmployeeResponseDto() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val employeeId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkEmployeeRepository successfully finds
      val workEmployee = WorkEmployeeMother.complete().build();
      given(workEmployeeRepository.findByWorkIdAndEmployeeIdAndWorkUserId(same(workId), same(employeeId), same(userId))).willReturn(Optional.of(workEmployee));
      // ... WorkEmployeeToFullWorkEmployeeResponseDtoConverter successfully converts
      val expectedResponseDto = FullWorkEmployeeResponseDtoMother.complete().build();
      given(toFullWorkEmployeeResponseDtoConverter.convert(same(workEmployee))).willReturn(expectedResponseDto);

      // WHEN
      // ... getWorkEmployeeByWorkIdAndEmployeeId is called
      val responseDto = workEmployeeService.getWorkEmployeeByWorkIdAndEmployeeId(workId, employeeId);

      // THEN
      // ... FullWorkEmployeeResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createWorkEmployeeForWorkId is called")
  public class WHEN_createWorkEmployeeForWorkId {

    @Test
    @DisplayName("GIVEN workId and CreateWorkEmployeeRequestDto " +
      "... THEN WorkEmployeeResponseDto")
    public void GIVEN_LongAndCreateWorkEmployeeRequestDto_THEN_WorkEmployeeResponseDto() {
      // GIVEN
      // ... workId
      val workId = 1L;
      // ... CreateWorkEmployeeRequestDto
      val requestDto = CreateWorkEmployeeRequestDtoMother.complete().build();
      // ... WorkService successfully gets Work by id
      val work = WorkMother.complete().build();
      given(workService.getById(same(workId))).willReturn(work);
      // ... CreateWorkEmployeeRequestDtoToJoinedCreateWorkEmployeeRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedCreateWorkEmployeeRequestDtoMother.complete().build();
      given(toJoinedCreateWorkEmployeeRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... JoinedCreateWorkEmployeeRequestDtoWithWorkAdapterValidator successfully validates
      willDoNothing().given(joinedCreateWorkEmployeeRequestDtoWithWorkAdapterValidator).validate(any(JoinedCreateWorkEmployeeRequestDtoWithWorkAdapter.class));
      // ... JoinedCreateWorkEmployeeRequestDtoWithWorkAdapterToWorkEmployeeConverter successfully converts
      val workEmployee = WorkEmployeeMother.complete().build();
      given(fromJoinedCreateWorkEmployeeRequestDtoWithWorkAdapterConverter.convert(any(JoinedCreateWorkEmployeeRequestDtoWithWorkAdapter.class))).willReturn(workEmployee);
      // ... WorkEmployeeRepository successfully saves
      given(workEmployeeRepository.save(same(workEmployee))).willReturn(workEmployee);
      // ... WorkEmployeeToFullWorkEmployeeResponseDtoConverter successfully converts
      val expectedResponseDto = FullWorkEmployeeResponseDtoMother.complete().build();
      given(toFullWorkEmployeeResponseDtoConverter.convert(same(workEmployee))).willReturn(expectedResponseDto);

      // WHEN
      // ... createWorkEmployeeForWorkId is called
      val responseDto = workEmployeeService.createWorkEmployeeForWorkId(workId, requestDto);

      // THEN
      // ... WorkEmployeeResponseDto
      then(workEmployeeRepository).should(times(1)).save(same(workEmployee));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateWorkEmployeeByWorkIdAndEmployeeId is called")
  public class WHEN_updateWorkEmployeeByWorkIdAndEmployeeId {

    @Test
    @DisplayName("GIVEN workId, employeeId, and UpdateWorkEmployeeRequestDto " +
      "... THEN WorkEmployeeResponseDto is returned")
    public void GIVEN_idsAndUpdateWorkEmployeeRequestDto_THEN_WorkEmployeeResponseDto() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val employeeId = 1L;
      // ... UpdateWorkEmployeeRequestDto
      val requestDto = UpdateWorkEmployeeRequestDtoMother.complete().build();
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkEmployeeRepository successfully finds
      val workEmployee = WorkEmployeeMother.complete().build();
      given(workEmployeeRepository.findByWorkIdAndEmployeeIdAndWorkUserId(same(workId), same(employeeId), same(userId))).willReturn(Optional.of(workEmployee));
      // ... WorkEmployeeMapper successfully partially updates WorkEmployee with UpdateWorkEmployeeRequestDto
      given(workEmployeeMapper.partialUpdate(same(workEmployee), same(requestDto))).willReturn(workEmployee);
      // ... WorkEmployeeRepository successfully saves WorkEmployee
      given(workEmployeeRepository.save(same(workEmployee))).willReturn(workEmployee);
      // ... WorkEmployeeToFullWorkEmployeeResponseDtoConverter successfully converts
      val expectedResponseDto = FullWorkEmployeeResponseDtoMother.complete().build();
      given(toFullWorkEmployeeResponseDtoConverter.convert(same(workEmployee))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateWorkEmployeeByWorkIdAndEmployeeId is called
      val responseDto = workEmployeeService.updateWorkEmployeeByWorkIdAndEmployeeId(workId, employeeId, requestDto);

      // THEN
      // ... WorkEmployeeResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteWorkEmployeeByWorkIdAndEmployeeId is called")
  public class WHEN_deleteWorkEmployeeByWorkIdAndEmployeeId {

    @Test
    @DisplayName("GIVEN workId and employeeId " +
      "... THEN void")
    public void GIVEN_ids_THEN_void() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val employeeId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkEmployeeRepository successfully finds
      val workEmployee = WorkEmployeeMother.complete().build();
      given(workEmployeeRepository.findByWorkIdAndEmployeeIdAndWorkUserId(same(workId), same(employeeId), same(userId))).willReturn(Optional.of(workEmployee));
      // ... WorkEmployeeRepository successfully deletes WorkEmployee
      willDoNothing().given(workEmployeeRepository).delete(workEmployee);

      // WHEN
      // ... deleteWorkEmployeeByWorkIdAndEmployeeId is called
      workEmployeeService.deleteWorkEmployeeByWorkIdAndEmployeeId(workId, employeeId);

      // THEN
      // ... void
    }

  }

  @Nested
  @DisplayName("WHEN getById is called")
  public class WHEN_getById {

    @Test
    @DisplayName("GIVEN invalid workId and employeeId " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidIds_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val employeeId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkEmployeeRepository fails to find
      given(workEmployeeRepository.findByWorkIdAndEmployeeIdAndWorkUserId(same(workId), same(employeeId), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> workEmployeeService.getByWorkIdAndEmployeeId(workId, employeeId));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN workId and employeeId " +
      "... THEN WorkEmployee is returned")
    public void GIVEN_ids_THEN_WorkEmployee() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val employeeId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkEmployeeRepository successfully finds
      val expectedWorkEmployee = WorkEmployeeMother.complete().build();
      given(workEmployeeRepository.findByWorkIdAndEmployeeIdAndWorkUserId(same(workId), same(employeeId), same(userId))).willReturn(Optional.of(expectedWorkEmployee));

      // WHEN
      // ... getById is called
      val returnedWorkEmployee = workEmployeeService.getByWorkIdAndEmployeeId(workId, employeeId);

      // THEN
      // ... WorkEmployeeResponseDto is returned
      and.then(returnedWorkEmployee)
        .isNotNull()
        .isEqualTo(expectedWorkEmployee);
    }

  }

}