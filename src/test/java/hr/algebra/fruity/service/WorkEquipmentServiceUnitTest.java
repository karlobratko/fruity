package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateWorkEquipmentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.dto.response.FullWorkEquipmentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.WorkEquipmentMapper;
import hr.algebra.fruity.model.WorkEquipment;
import hr.algebra.fruity.repository.WorkEquipmentRepository;
import hr.algebra.fruity.service.impl.CurrentUserWorkEquipmentService;
import hr.algebra.fruity.utils.mother.dto.CreateWorkEquipmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullWorkEquipmentResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateWorkEquipmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.UserMother;
import hr.algebra.fruity.utils.mother.model.WorkEquipmentMother;
import hr.algebra.fruity.utils.mother.model.WorkMother;
import hr.algebra.fruity.validator.CreateWorkEquipmentRequestDtoWithWorkAdapterValidator;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("WorkEquipment Service Unit Test")
@SuppressWarnings("static-access")
public class WorkEquipmentServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserWorkEquipmentService workEquipmentService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private CreateWorkEquipmentRequestDtoWithWorkAdapterValidator createWorkEquipmentRequestDtoWithWorkAdapterValidator;

  @Mock
  private WorkEquipmentMapper workEquipmentMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private WorkEquipmentRepository workEquipmentRepository;

  @Mock
  private WorkService workService;

  @Nested
  @DisplayName("WHEN getWorkEquipmentByWorkIdAndEquipmentId is called")
  public class WHEN_getWorkEquipmentByWorkIdAndEquipmentId {

    @Test
    @DisplayName("GIVEN workId and attachmentId " +
      "... THEN WorkEquipmentResponseDto is returned")
    public void GIVEN_ids_THEN_WorkEquipmentResponseDto() {
      // GIVEN
      // ... ids
      val workId = 1L;
      val attachmentId = 1L;
      val workEquipment = WorkEquipmentMother.complete().build();
      given(workEquipmentRepository.findByWorkIdAndEquipmentId(same(workId), same(attachmentId))).willReturn(Optional.of(workEquipment));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = workEquipment.getWork().getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... ConversionService successfully converts from User to FullWorkEquipmentResponseDto
      val expectedResponseDto = FullWorkEquipmentResponseDtoMother.complete().build();
      given(conversionService.convert(same(workEquipment), same(FullWorkEquipmentResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... getWorkEquipmentByWorkIdAndEquipmentId is called
      val responseDto = workEquipmentService.getWorkEquipmentByWorkIdAndEquipmentId(workId, attachmentId);

      // THEN
      // ... FullWorkEquipmentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createWorkEquipmentForWorkId is called")
  public class WHEN_createWorkEquipmentForWorkId {

    @Test
    @DisplayName("GIVEN workId and CreateWorkEquipmentRequestDto " +
      "... THEN WorkEquipmentResponseDto")
    public void GIVEN_LongAndCreateWorkEquipmentRequestDto_THEN_WorkEquipmentResponseDto() {
      // GIVEN
      // ... workId
      val workId = 1L;
      // ... CreateWorkEquipmentRequestDto
      val requestDto = CreateWorkEquipmentRequestDtoMother.complete().build();
      // ... WorkService successfully gets Work by id
      val work = WorkMother.complete().build();
      given(workService.getById(same(workId))).willReturn(work);
      // ... CreateWorkEquipmentRequestDtoWithWorkAdapterValidator successfully validates requestDto and Work
      willDoNothing().given(createWorkEquipmentRequestDtoWithWorkAdapterValidator).validate(any(CreateWorkEquipmentRequestDtoWithWorkAdapter.class));
      // ... ConversionService successfully converts from CreateWorkEquipmentRequestDtoWithWorkAdapter to WorkEquipment
      val workEquipment = WorkEquipmentMother.complete().build();
      given(conversionService.convert(any(CreateWorkEquipmentRequestDtoWithWorkAdapter.class), same(WorkEquipment.class))).willReturn(workEquipment);
      // ... WorkEquipmentRepository will successfully save WorkEquipment
      given(workEquipmentRepository.save(same(workEquipment))).willReturn(workEquipment);
      // ... ConversionService successfully converts from WorkEquipment to FullWorkEquipmentResponseDto
      val expectedResponseDto = FullWorkEquipmentResponseDtoMother.complete().build();
      given(conversionService.convert(same(workEquipment), same(FullWorkEquipmentResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... createWorkEquipmentForWorkId is called
      val responseDto = workEquipmentService.createWorkEquipmentForWorkId(workId, requestDto);

      // THEN
      // ... WorkEquipmentResponseDto
      then(workEquipmentRepository).should(times(1)).save(same(workEquipment));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateWorkEquipmentByWorkIdAndEquipmentId is called")
  public class WHEN_updateWorkEquipmentByWorkIdAndEquipmentId {

    @Test
    @DisplayName("GIVEN workId, attachmentId, and UpdateWorkEquipmentRequestDto " +
      "... THEN WorkEquipmentResponseDto is returned")
    public void GIVEN_idsAndUpdateWorkEquipmentRequestDto_THEN_WorkEquipmentResponseDto() {
      // GIVEN
      // ... ids
      val workId = 1L;
      val attachmentId = 1L;
      val workEquipment = WorkEquipmentMother.complete().build();
      given(workEquipmentRepository.findByWorkIdAndEquipmentId(same(workId), same(attachmentId))).willReturn(Optional.of(workEquipment));
      // ... UpdateWorkEquipmentRequestDto
      val requestDto = UpdateWorkEquipmentRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = workEquipment.getWork().getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... WorkEquipmentMapper successfully partially updates WorkEquipment with UpdateWorkEquipmentRequestDto
      given(workEquipmentMapper.partialUpdate(same(workEquipment), same(requestDto))).willReturn(workEquipment);
      // ... WorkEquipmentRepository successfully saves WorkEquipment
      given(workEquipmentRepository.save(same(workEquipment))).willReturn(workEquipment);
      // ... ConversionService successfully converts from WorkEquipment to FullWorkEquipmentResponseDto
      val expectedResponseDto = FullWorkEquipmentResponseDtoMother.complete().build();
      given(conversionService.convert(same(workEquipment), same(FullWorkEquipmentResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateWorkEquipmentByWorkIdAndEquipmentId is called
      val responseDto = workEquipmentService.updateWorkEquipmentByWorkIdAndEquipmentId(workId, attachmentId, requestDto);

      // THEN
      // ... WorkEquipmentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteWorkEquipmentByWorkIdAndEquipmentId is called")
  public class WHEN_deleteWorkEquipmentByWorkIdAndEquipmentId {

    @Test
    @DisplayName("GIVEN workId and attachmentId " +
      "... THEN void")
    public void GIVEN_ids_THEN_void() {
      // GIVEN
      // ... ids
      val workId = 1L;
      val attachmentId = 1L;
      val workEquipment = WorkEquipmentMother.complete().build();
      given(workEquipmentRepository.findByWorkIdAndEquipmentId(same(workId), same(attachmentId))).willReturn(Optional.of(workEquipment));
      // ... CurrentUserService's logged-in User is not equal to WorkEquipment User
      val loggedInUser = workEquipment.getWork().getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... WorkEquipmentRepository successfully deletes WorkEquipment
      willDoNothing().given(workEquipmentRepository).delete(workEquipment);

      // WHEN
      // ... deleteWorkEquipmentByWorkIdAndEquipmentId is called
      workEquipmentService.deleteWorkEquipmentByWorkIdAndEquipmentId(workId, attachmentId);

      // THEN
      // ... void
    }

  }

  @Nested
  @DisplayName("WHEN getById is called")
  public class WHEN_getById {

    @Test
    @DisplayName("GIVEN invalid workId and attachmentId " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidIds_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val attachmentId = 1L;
      given(workEquipmentRepository.findByWorkIdAndEquipmentId(same(workId), same(attachmentId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> workEquipmentService.getByWorkIdAndEquipmentId(workId, attachmentId));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(EntityNotFoundException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN workId, attachmentId, and foreign logged-in User " +
      "... THEN ForeignUserDataAccessException is thrown")
    public void GIVEN_idsAndForeignUser_THEN_ForeignUserDataAccessException() {
      // GIVEN
      // ... ids
      val workId = 1L;
      val attachmentId = 1L;
      val workEquipment = WorkEquipmentMother.complete().build();
      given(workEquipmentRepository.findByWorkIdAndEquipmentId(same(workId), same(attachmentId))).willReturn(Optional.of(workEquipment));
      // ... CurrentUserService's logged-in User is not equal to WorkEquipment User
      val loggedInUser = UserMother.complete().id(workEquipment.getWork().getUser().getId() + 1).build();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getById is called
      when(() -> workEquipmentService.getByWorkIdAndEquipmentId(workId, attachmentId));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN workId and attachmentId " +
      "... THEN WorkEquipment is returned")
    public void GIVEN_ids_THEN_WorkEquipment() {
      // GIVEN
      // ... ids
      val workId = 1L;
      val attachmentId = 1L;
      val expectedWorkEquipment = WorkEquipmentMother.complete().build();
      given(workEquipmentRepository.findByWorkIdAndEquipmentId(same(workId), same(attachmentId))).willReturn(Optional.of(expectedWorkEquipment));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = expectedWorkEquipment.getWork().getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getById is called
      val returnedWorkEquipment = workEquipmentService.getByWorkIdAndEquipmentId(workId, attachmentId);

      // THEN
      // ... WorkEquipmentResponseDto is returned
      and.then(returnedWorkEquipment)
        .isNotNull()
        .isEqualTo(expectedWorkEquipment);
    }

  }

}