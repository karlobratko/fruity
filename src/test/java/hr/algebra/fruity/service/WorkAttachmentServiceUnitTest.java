package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.CreateWorkAttachmentRequestDtoToJoinedCreateWorkAttachmentRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateWorkAttachmentRequestDtoWithWorkAdapterToWorkAttachmentConverter;
import hr.algebra.fruity.converter.WorkAttachmentToFullWorkAttachmentResponseDtoConverter;
import hr.algebra.fruity.converter.WorkAttachmentToWorkAttachmentResponseDtoConverter;
import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkAttachmentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.WorkAttachmentMapper;
import hr.algebra.fruity.repository.WorkAttachmentRepository;
import hr.algebra.fruity.service.impl.CurrentUserWorkAttachmentService;
import hr.algebra.fruity.utils.mother.dto.CreateWorkAttachmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullWorkAttachmentResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedCreateWorkAttachmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateWorkAttachmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.WorkAttachmentMother;
import hr.algebra.fruity.utils.mother.model.WorkMother;
import hr.algebra.fruity.validator.JoinedCreateWorkAttachmentRequestDtoWithWorkAdapterValidator;
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
@DisplayName("WorkAttachment Service Unit Test")
@SuppressWarnings("static-access")
public class WorkAttachmentServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserWorkAttachmentService workAttachmentService;

  @Mock
  private WorkAttachmentToWorkAttachmentResponseDtoConverter toWorkAttachmentResponseDtoConverter;

  @Mock
  private WorkAttachmentToFullWorkAttachmentResponseDtoConverter toFullWorkAttachmentResponseDtoConverter;

  @Mock
  private CreateWorkAttachmentRequestDtoToJoinedCreateWorkAttachmentRequestDtoConverter toJoinedCreateWorkAttachmentRequestDtoConverter;

  @Mock
  private JoinedCreateWorkAttachmentRequestDtoWithWorkAdapterToWorkAttachmentConverter fromJoinedCreateWorkAttachmentRequestDtoWithWorkAdapterConverter;

  @Mock
  private JoinedCreateWorkAttachmentRequestDtoWithWorkAdapterValidator joinedCreateWorkAttachmentRequestDtoWithWorkAdapterValidator;

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
    @DisplayName("GIVEN workId and attachmentId " +
      "... THEN WorkAttachmentResponseDto is returned")
    public void GIVEN_ids_THEN_WorkAttachmentResponseDto() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val attachmentId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkAttachmentRepository successfully finds
      val workAttachment = WorkAttachmentMother.complete().build();
      given(workAttachmentRepository.findByWorkIdAndAttachmentIdAndWorkUserId(same(workId), same(attachmentId), same(userId))).willReturn(Optional.of(workAttachment));
      // ... WorkAttachmentToFullWorkAttachmentResponseDtoConverter successfully converts
      val expectedResponseDto = FullWorkAttachmentResponseDtoMother.complete().build();
      given(toFullWorkAttachmentResponseDtoConverter.convert(same(workAttachment))).willReturn(expectedResponseDto);

      // WHEN
      // ... getWorkAttachmentByWorkIdAndAttachmentId is called
      val responseDto = workAttachmentService.getWorkAttachmentByWorkIdAndAttachmentId(workId, attachmentId);

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
      // ... CreateWorkAttachmentRequestDtoToJoinedCreateWorkAttachmentRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedCreateWorkAttachmentRequestDtoMother.complete().build();
      given(toJoinedCreateWorkAttachmentRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... JoinedCreateWorkAttachmentRequestDtoWithWorkAdapterValidator successfully validates
      willDoNothing().given(joinedCreateWorkAttachmentRequestDtoWithWorkAdapterValidator).validate(any(JoinedCreateWorkAttachmentRequestDtoWithWorkAdapter.class));
      // ... JoinedCreateWorkAttachmentRequestDtoWithWorkAdapterToWorkAttachmentConverter successfully converts
      val workAttachment = WorkAttachmentMother.complete().build();
      given(fromJoinedCreateWorkAttachmentRequestDtoWithWorkAdapterConverter.convert(any(JoinedCreateWorkAttachmentRequestDtoWithWorkAdapter.class))).willReturn(workAttachment);
      // ... WorkAttachmentRepository successfully saves
      given(workAttachmentRepository.save(same(workAttachment))).willReturn(workAttachment);
      // ... WorkAttachmentToFullWorkAttachmentResponseDtoConverter successfully converts
      val expectedResponseDto = FullWorkAttachmentResponseDtoMother.complete().build();
      given(toFullWorkAttachmentResponseDtoConverter.convert(same(workAttachment))).willReturn(expectedResponseDto);

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
    @DisplayName("GIVEN workId, attachmentId, and UpdateWorkAttachmentRequestDto " +
      "... THEN WorkAttachmentResponseDto is returned")
    public void GIVEN_idsAndUpdateWorkAttachmentRequestDto_THEN_WorkAttachmentResponseDto() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val attachmentId = 1L;
      // ... UpdateWorkAttachmentRequestDto
      val requestDto = UpdateWorkAttachmentRequestDtoMother.complete().build();
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkAttachmentRepository successfully finds
      val workAttachment = WorkAttachmentMother.complete().build();
      given(workAttachmentRepository.findByWorkIdAndAttachmentIdAndWorkUserId(same(workId), same(attachmentId), same(userId))).willReturn(Optional.of(workAttachment));
      // ... WorkAttachmentMapper successfully partially updates WorkAttachment with UpdateWorkAttachmentRequestDto
      given(workAttachmentMapper.partialUpdate(same(workAttachment), same(requestDto))).willReturn(workAttachment);
      // ... WorkAttachmentRepository successfully saves WorkAttachment
      given(workAttachmentRepository.save(same(workAttachment))).willReturn(workAttachment);
      // ... WorkAttachmentToFullWorkAttachmentResponseDtoConverter successfully converts
      val expectedResponseDto = FullWorkAttachmentResponseDtoMother.complete().build();
      given(toFullWorkAttachmentResponseDtoConverter.convert(same(workAttachment))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateWorkAttachmentByWorkIdAndAttachmentId is called
      val responseDto = workAttachmentService.updateWorkAttachmentByWorkIdAndAttachmentId(workId, attachmentId, requestDto);

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
    @DisplayName("GIVEN workId and attachmentId " +
      "... THEN void")
    public void GIVEN_ids_THEN_void() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val attachmentId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkAttachmentRepository successfully finds
      val workAttachment = WorkAttachmentMother.complete().build();
      given(workAttachmentRepository.findByWorkIdAndAttachmentIdAndWorkUserId(same(workId), same(attachmentId), same(userId))).willReturn(Optional.of(workAttachment));
      // ... WorkAttachmentRepository successfully deletes WorkAttachment
      willDoNothing().given(workAttachmentRepository).delete(workAttachment);

      // WHEN
      // ... deleteWorkAttachmentByWorkIdAndAttachmentId is called
      workAttachmentService.deleteWorkAttachmentByWorkIdAndAttachmentId(workId, attachmentId);

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
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkAttachmentRepository fails to find
      given(workAttachmentRepository.findByWorkIdAndAttachmentIdAndWorkUserId(same(workId), same(attachmentId), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> workAttachmentService.getByWorkIdAndAttachmentId(workId, attachmentId));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN workId and attachmentId " +
      "... THEN WorkAttachment is returned")
    public void GIVEN_ids_THEN_WorkAttachment() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val attachmentId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkAttachmentRepository successfully finds
      val expectedWorkAttachment = WorkAttachmentMother.complete().build();
      given(workAttachmentRepository.findByWorkIdAndAttachmentIdAndWorkUserId(same(workId), same(attachmentId), same(userId))).willReturn(Optional.of(expectedWorkAttachment));

      // WHEN
      // ... getById is called
      val returnedWorkAttachment = workAttachmentService.getByWorkIdAndAttachmentId(workId, attachmentId);

      // THEN
      // ... WorkAttachmentResponseDto is returned
      and.then(returnedWorkAttachment)
        .isNotNull()
        .isEqualTo(expectedWorkAttachment);
    }

  }

}