package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.CreateRealisationAttachmentRequestDtoToJoinedCreateRealisationAttachmentRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterToRealisationAttachmentConverter;
import hr.algebra.fruity.converter.RealisationAttachmentToFullRealisationAttachmentResponseDtoConverter;
import hr.algebra.fruity.converter.RealisationAttachmentToRealisationAttachmentResponseDtoConverter;
import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.RealisationAttachmentMapper;
import hr.algebra.fruity.repository.RealisationAttachmentRepository;
import hr.algebra.fruity.service.impl.CurrentUserRealisationAttachmentService;
import hr.algebra.fruity.utils.mother.dto.CreateRealisationAttachmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullRealisationAttachmentResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedCreateRealisationAttachmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateRealisationAttachmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.RealisationAttachmentMother;
import hr.algebra.fruity.utils.mother.model.RealisationMother;
import hr.algebra.fruity.validator.JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterValidator;
import hr.algebra.fruity.validator.RealisationAttachmentWithUpdateRealisationAttachmentRequestDtoValidator;
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
@DisplayName("RealisationAttachment Service Unit Test")
@SuppressWarnings("static-access")
public class RealisationAttachmentServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserRealisationAttachmentService realisationAttachmentService;

  @Mock
  private RealisationAttachmentToRealisationAttachmentResponseDtoConverter toRealisationAttachmentResponseDtoConverter;

  @Mock
  private RealisationAttachmentToFullRealisationAttachmentResponseDtoConverter toFullRealisationAttachmentResponseDtoConverter;

  @Mock
  private CreateRealisationAttachmentRequestDtoToJoinedCreateRealisationAttachmentRequestDtoConverter toJoinedCreateRealisationAttachmentRequestDtoConverter;

  @Mock
  private JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterToRealisationAttachmentConverter fromJoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterConverter;

  @Mock
  private JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterValidator joinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterValidator;

  @Mock
  private RealisationAttachmentWithUpdateRealisationAttachmentRequestDtoValidator realisationAttachmentWithUpdateRealisationAttachmentRequestDtoValidator;

  @Mock
  private RealisationAttachmentMapper realisationAttachmentMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private RealisationAttachmentRepository realisationAttachmentRepository;

  @Mock
  private RealisationService realisationService;

  @Nested
  @DisplayName("WHEN getRealisationAttachmentByRealisationIdAndAttachmentId is called")
  public class WHEN_getRealisationAttachmentByRealisationIdAndAttachmentId {

    @Test
    @DisplayName("GIVEN realisationId and attachmentId " +
      "... THEN RealisationAttachmentResponseDto is returned")
    public void GIVEN_ids_THEN_RealisationAttachmentResponseDto() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val attachmentId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationAttachmentRepository successfully finds
      val realisationAttachment = RealisationAttachmentMother.complete().build();
      given(realisationAttachmentRepository.findByRealisationIdAndAttachmentIdAndRealisationWorkUserId(same(realisationId), same(attachmentId), same(userId))).willReturn(Optional.of(realisationAttachment));
      // ... RealisationAttachmentToFullRealisationAttachmentResponseDtoConverter successfully converts
      val expectedResponseDto = FullRealisationAttachmentResponseDtoMother.complete().build();
      given(toFullRealisationAttachmentResponseDtoConverter.convert(same(realisationAttachment))).willReturn(expectedResponseDto);

      // WHEN
      // ... getRealisationAttachmentByRealisationIdAndAttachmentId is called
      val responseDto = realisationAttachmentService.getRealisationAttachmentByRealisationIdAndAttachmentId(realisationId, attachmentId);

      // THEN
      // ... FullRealisationAttachmentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createRealisationAttachmentForRealisationId is called")
  public class WHEN_createRealisationAttachmentForRealisationId {

    @Test
    @DisplayName("GIVEN realisationId and CreateRealisationAttachmentRequestDto " +
      "... THEN RealisationAttachmentResponseDto")
    public void GIVEN_LongAndCreateRealisationAttachmentRequestDto_THEN_RealisationAttachmentResponseDto() {
      // GIVEN
      // ... realisationId
      val realisationId = 1L;
      // ... CreateRealisationAttachmentRequestDto
      val requestDto = CreateRealisationAttachmentRequestDtoMother.complete().build();
      // ... RealisationService successfully gets Realisation by id
      val realisation = RealisationMother.complete().build();
      given(realisationService.getById(same(realisationId))).willReturn(realisation);
      // ... CreateRealisationAttachmentRequestDtoToJoinedCreateRealisationAttachmentRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedCreateRealisationAttachmentRequestDtoMother.complete().build();
      given(toJoinedCreateRealisationAttachmentRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterValidator successfully validates
      willDoNothing().given(joinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterValidator).validate(any(JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapter.class));
      // ... JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterToRealisationAttachmentConverter successfully converts
      val realisationAttachment = RealisationAttachmentMother.complete().build();
      given(fromJoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterConverter.convert(any(JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapter.class))).willReturn(realisationAttachment);
      // ... RealisationAttachmentRepository successfully saves
      given(realisationAttachmentRepository.save(same(realisationAttachment))).willReturn(realisationAttachment);
      // ... RealisationAttachmentToFullRealisationAttachmentResponseDtoConverter successfully converts
      val expectedResponseDto = FullRealisationAttachmentResponseDtoMother.complete().build();
      given(toFullRealisationAttachmentResponseDtoConverter.convert(same(realisationAttachment))).willReturn(expectedResponseDto);

      // WHEN
      // ... createRealisationAttachmentForRealisationId is called
      val responseDto = realisationAttachmentService.createRealisationAttachmentForRealisationId(realisationId, requestDto);

      // THEN
      // ... RealisationAttachmentResponseDto
      then(realisationAttachmentRepository).should(times(1)).save(same(realisationAttachment));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateRealisationAttachmentByRealisationIdAndAttachmentId is called")
  public class WHEN_updateRealisationAttachmentByRealisationIdAndAttachmentId {

    @Test
    @DisplayName("GIVEN realisationId, attachmentId, and UpdateRealisationAttachmentRequestDto " +
      "... THEN RealisationAttachmentResponseDto is returned")
    public void GIVEN_idsAndUpdateRealisationAttachmentRequestDto_THEN_RealisationAttachmentResponseDto() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val attachmentId = 1L;
      // ... UpdateRealisationAttachmentRequestDto
      val requestDto = UpdateRealisationAttachmentRequestDtoMother.complete().build();
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationAttachmentRepository successfully finds
      val realisationAttachment = RealisationAttachmentMother.complete().build();
      given(realisationAttachmentRepository.findByRealisationIdAndAttachmentIdAndRealisationWorkUserId(same(realisationId), same(attachmentId), same(userId))).willReturn(Optional.of(realisationAttachment));
      // ... RealisationAttachmentWithUpdateRealisationAttachmentRequestDtoValidator successfully validates
      willDoNothing().given(realisationAttachmentWithUpdateRealisationAttachmentRequestDtoValidator).validate(same(realisationAttachment), same(requestDto));
      // ... RealisationAttachmentMapper successfully partially updates RealisationAttachment with UpdateRealisationAttachmentRequestDto
      given(realisationAttachmentMapper.partialUpdate(same(realisationAttachment), same(requestDto))).willReturn(realisationAttachment);
      // ... RealisationAttachmentRepository successfully saves RealisationAttachment
      given(realisationAttachmentRepository.save(same(realisationAttachment))).willReturn(realisationAttachment);
      // ... RealisationAttachmentToFullRealisationAttachmentResponseDtoConverter successfully converts
      val expectedResponseDto = FullRealisationAttachmentResponseDtoMother.complete().build();
      given(toFullRealisationAttachmentResponseDtoConverter.convert(same(realisationAttachment))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateRealisationAttachmentByRealisationIdAndAttachmentId is called
      val responseDto = realisationAttachmentService.updateRealisationAttachmentByRealisationIdAndAttachmentId(realisationId, attachmentId, requestDto);

      // THEN
      // ... RealisationAttachmentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteRealisationAttachmentByRealisationIdAndAttachmentId is called")
  public class WHEN_deleteRealisationAttachmentByRealisationIdAndAttachmentId {

    @Test
    @DisplayName("GIVEN realisationId and attachmentId " +
      "... THEN void")
    public void GIVEN_ids_THEN_void() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val attachmentId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationAttachmentRepository successfully finds
      val realisationAttachment = RealisationAttachmentMother.complete().build();
      given(realisationAttachmentRepository.findByRealisationIdAndAttachmentIdAndRealisationWorkUserId(same(realisationId), same(attachmentId), same(userId))).willReturn(Optional.of(realisationAttachment));
      // ... RealisationAttachmentRepository successfully deletes RealisationAttachment
      willDoNothing().given(realisationAttachmentRepository).delete(realisationAttachment);

      // WHEN
      // ... deleteRealisationAttachmentByRealisationIdAndAttachmentId is called
      realisationAttachmentService.deleteRealisationAttachmentByRealisationIdAndAttachmentId(realisationId, attachmentId);

      // THEN
      // ... void
    }

  }

  @Nested
  @DisplayName("WHEN getById is called")
  public class WHEN_getById {

    @Test
    @DisplayName("GIVEN invalid realisationId and attachmentId " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidIds_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val attachmentId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationAttachmentRepository fails to find
      given(realisationAttachmentRepository.findByRealisationIdAndAttachmentIdAndRealisationWorkUserId(same(realisationId), same(attachmentId), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> realisationAttachmentService.getByRealisationIdAndAttachmentId(realisationId, attachmentId));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN realisationId and attachmentId " +
      "... THEN RealisationAttachment is returned")
    public void GIVEN_ids_THEN_RealisationAttachment() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val attachmentId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationAttachmentRepository successfully finds
      val expectedRealisationAttachment = RealisationAttachmentMother.complete().build();
      given(realisationAttachmentRepository.findByRealisationIdAndAttachmentIdAndRealisationWorkUserId(same(realisationId), same(attachmentId), same(userId))).willReturn(Optional.of(expectedRealisationAttachment));

      // WHEN
      // ... getById is called
      val returnedRealisationAttachment = realisationAttachmentService.getByRealisationIdAndAttachmentId(realisationId, attachmentId);

      // THEN
      // ... RealisationAttachmentResponseDto is returned
      and.then(returnedRealisationAttachment)
        .isNotNull()
        .isEqualTo(expectedRealisationAttachment);
    }

  }

}