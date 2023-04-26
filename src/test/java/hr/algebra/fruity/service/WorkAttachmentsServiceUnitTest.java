package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateWorkAttachmentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.dto.response.FullWorkAttachmentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.WorkAttachmentMapper;
import hr.algebra.fruity.model.WorkAttachment;
import hr.algebra.fruity.repository.WorkAttachmentRepository;
import hr.algebra.fruity.service.impl.CurrentUserWorkAttachmentService;
import hr.algebra.fruity.utils.mother.dto.CreateWorkAttachmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullWorkAttachmentResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateWorkAttachmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.UserMother;
import hr.algebra.fruity.utils.mother.model.WorkAttachmentMother;
import hr.algebra.fruity.utils.mother.model.WorkMother;
import hr.algebra.fruity.validator.CreateWorkAttachmentRequestDtoWithWorkAdapterValidator;
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
@DisplayName("WorkAttachment Service Unit Test")
@SuppressWarnings("static-access")
public class WorkAttachmentsServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserWorkAttachmentService workAttachmentService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private CreateWorkAttachmentRequestDtoWithWorkAdapterValidator createWorkAttachmentRequestDtoWithWorkAdapterValidator;

  @Mock
  private WorkAttachmentMapper workAttachmentMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private WorkAttachmentRepository workAttachmentRepository;

  @Mock
  private WorkService workService;

  @Nested
  @DisplayName("WHEN getWorkAttachmentByWorkIdAndAttachmentId is called")
  public class WHEN_getWorkAttachmentByWorkIdAndAttachmentId {

    @Test
    @DisplayName("GIVEN workId and agentId " +
      "... THEN WorkAttachmentResponseDto is returned")
    public void GIVEN_ids_THEN_WorkAttachmentResponseDto() {
      // GIVEN
      // ... ids
      val workId = 1L;
      val agentId = 1L;
      val workAttachment = WorkAttachmentMother.complete().build();
      given(workAttachmentRepository.findByWorkIdAndAttachmentId(same(workId), same(agentId))).willReturn(Optional.of(workAttachment));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = workAttachment.getWork().getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... ConversionService successfully converts from User to FullWorkAttachmentResponseDto
      val expectedResponseDto = FullWorkAttachmentResponseDtoMother.complete().build();
      given(conversionService.convert(same(workAttachment), same(FullWorkAttachmentResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... getWorkAttachmentByWorkIdAndAttachmentId is called
      val responseDto = workAttachmentService.getWorkAttachmentByWorkIdAndAttachmentId(workId, agentId);

      // THEN
      // ... FullWorkAttachmentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createWorkAttachmentForWorkId is called")
  public class WHEN_createWorkAttachmentForWorkId {

    @Test
    @DisplayName("GIVEN workId and CreateWorkAttachmentRequestDto " +
      "... THEN WorkAttachmentResponseDto")
    public void GIVEN_LongAndCreateWorkAttachmentRequestDto_THEN_WorkAttachmentResponseDto() {
      // GIVEN
      // ... workId
      val workId = 1L;
      // ... CreateWorkAttachmentRequestDto
      val requestDto = CreateWorkAttachmentRequestDtoMother.complete().build();
      // ... WorkService successfully gets Work by id
      val work = WorkMother.complete().build();
      given(workService.getById(same(workId))).willReturn(work);
      // ... CreateWorkAttachmentRequestDtoWithWorkAdapterValidator successfully validates requestDto and Work
      willDoNothing().given(createWorkAttachmentRequestDtoWithWorkAdapterValidator).validate(any(CreateWorkAttachmentRequestDtoWithWorkAdapter.class));
      // ... ConversionService successfully converts from CreateWorkAttachmentRequestDtoWithWorkAdapter to WorkAttachment
      val workAttachment = WorkAttachmentMother.complete().build();
      given(conversionService.convert(any(CreateWorkAttachmentRequestDtoWithWorkAdapter.class), same(WorkAttachment.class))).willReturn(workAttachment);
      // ... WorkAttachmentRepository will successfully save WorkAttachment
      given(workAttachmentRepository.save(same(workAttachment))).willReturn(workAttachment);
      // ... ConversionService successfully converts from WorkAttachment to FullWorkAttachmentResponseDto
      val expectedResponseDto = FullWorkAttachmentResponseDtoMother.complete().build();
      given(conversionService.convert(same(workAttachment), same(FullWorkAttachmentResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... createWorkAttachmentForWorkId is called
      val responseDto = workAttachmentService.createWorkAttachmentForWorkId(workId, requestDto);

      // THEN
      // ... WorkAttachmentResponseDto
      then(workAttachmentRepository).should(times(1)).save(same(workAttachment));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateWorkAttachmentByWorkIdAndAttachmentId is called")
  public class WHEN_updateWorkAttachmentByWorkIdAndAttachmentId {

    @Test
    @DisplayName("GIVEN workId, agentId, and UpdateWorkAttachmentRequestDto " +
      "... THEN WorkAttachmentResponseDto is returned")
    public void GIVEN_idsAndUpdateWorkAttachmentRequestDto_THEN_WorkAttachmentResponseDto() {
      // GIVEN
      // ... ids
      val workId = 1L;
      val agentId = 1L;
      val workAttachment = WorkAttachmentMother.complete().build();
      given(workAttachmentRepository.findByWorkIdAndAttachmentId(same(workId), same(agentId))).willReturn(Optional.of(workAttachment));
      // ... UpdateWorkAttachmentRequestDto
      val requestDto = UpdateWorkAttachmentRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = workAttachment.getWork().getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... WorkAttachmentMapper successfully partially updates WorkAttachment with UpdateWorkAttachmentRequestDto
      given(workAttachmentMapper.partialUpdate(same(workAttachment), same(requestDto))).willReturn(workAttachment);
      // ... WorkAttachmentRepository successfully saves WorkAttachment
      given(workAttachmentRepository.save(same(workAttachment))).willReturn(workAttachment);
      // ... ConversionService successfully converts from WorkAttachment to FullWorkAttachmentResponseDto
      val expectedResponseDto = FullWorkAttachmentResponseDtoMother.complete().build();
      given(conversionService.convert(same(workAttachment), same(FullWorkAttachmentResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateWorkAttachmentByWorkIdAndAttachmentId is called
      val responseDto = workAttachmentService.updateWorkAttachmentByWorkIdAndAttachmentId(workId, agentId, requestDto);

      // THEN
      // ... WorkAttachmentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteWorkAttachmentByWorkIdAndAttachmentId is called")
  public class WHEN_deleteWorkAttachmentByWorkIdAndAttachmentId {

    @Test
    @DisplayName("GIVEN workId and agentId " +
      "... THEN void")
    public void GIVEN_ids_THEN_void() {
      // GIVEN
      // ... ids
      val workId = 1L;
      val agentId = 1L;
      val workAttachment = WorkAttachmentMother.complete().build();
      given(workAttachmentRepository.findByWorkIdAndAttachmentId(same(workId), same(agentId))).willReturn(Optional.of(workAttachment));
      // ... CurrentUserService's logged-in User is not equal to WorkAttachment User
      val loggedInUser = workAttachment.getWork().getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... WorkAttachmentRepository successfully deletes WorkAttachment
      willDoNothing().given(workAttachmentRepository).delete(workAttachment);

      // WHEN
      // ... deleteWorkAttachmentByWorkIdAndAttachmentId is called
      workAttachmentService.deleteWorkAttachmentByWorkIdAndAttachmentId(workId, agentId);

      // THEN
      // ... void
    }

  }

  @Nested
  @DisplayName("WHEN getById is called")
  public class WHEN_getById {

    @Test
    @DisplayName("GIVEN invalid workId and agentId " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidIds_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val agentId = 1L;
      given(workAttachmentRepository.findByWorkIdAndAttachmentId(same(workId), same(agentId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> workAttachmentService.getByWorkIdAndAttachmentId(workId, agentId));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(EntityNotFoundException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN workId, agentId, and foreign logged-in User " +
      "... THEN ForeignUserDataAccessException is thrown")
    public void GIVEN_idsAndForeignUser_THEN_ForeignUserDataAccessException() {
      // GIVEN
      // ... ids
      val workId = 1L;
      val agentId = 1L;
      val workAttachment = WorkAttachmentMother.complete().build();
      given(workAttachmentRepository.findByWorkIdAndAttachmentId(same(workId), same(agentId))).willReturn(Optional.of(workAttachment));
      // ... CurrentUserService's logged-in User is not equal to WorkAttachment User
      val loggedInUser = UserMother.complete().id(workAttachment.getWork().getUser().getId() + 1).build();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getById is called
      when(() -> workAttachmentService.getByWorkIdAndAttachmentId(workId, agentId));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN workId and agentId " +
      "... THEN WorkAttachment is returned")
    public void GIVEN_ids_THEN_WorkAttachment() {
      // GIVEN
      // ... ids
      val workId = 1L;
      val agentId = 1L;
      val expectedWorkAttachment = WorkAttachmentMother.complete().build();
      given(workAttachmentRepository.findByWorkIdAndAttachmentId(same(workId), same(agentId))).willReturn(Optional.of(expectedWorkAttachment));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = expectedWorkAttachment.getWork().getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getById is called
      val returnedWorkAttachment = workAttachmentService.getByWorkIdAndAttachmentId(workId, agentId);

      // THEN
      // ... WorkAttachmentResponseDto is returned
      and.then(returnedWorkAttachment)
        .isNotNull()
        .isEqualTo(expectedWorkAttachment);
    }

  }

}