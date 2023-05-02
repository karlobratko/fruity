package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.CreateRealisationEquipmentRequestDtoToJoinedCreateRealisationEquipmentRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterToRealisationEquipmentConverter;
import hr.algebra.fruity.converter.RealisationEquipmentToFullRealisationEquipmentResponseDtoConverter;
import hr.algebra.fruity.converter.RealisationEquipmentToRealisationEquipmentResponseDtoConverter;
import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.RealisationEquipmentMapper;
import hr.algebra.fruity.repository.RealisationEquipmentRepository;
import hr.algebra.fruity.service.impl.CurrentUserRealisationEquipmentService;
import hr.algebra.fruity.utils.mother.dto.CreateRealisationEquipmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullRealisationEquipmentResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedCreateRealisationEquipmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateRealisationEquipmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.RealisationEquipmentMother;
import hr.algebra.fruity.utils.mother.model.RealisationMother;
import hr.algebra.fruity.validator.JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterValidator;
import hr.algebra.fruity.validator.RealisationEquipmentWithUpdateRealisationEquipmentRequestDtoValidator;
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
@DisplayName("RealisationEquipment Service Unit Test")
@SuppressWarnings("static-access")
public class RealisationEquipmentServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserRealisationEquipmentService realisationEquipmentService;

  @Mock
  private RealisationEquipmentToRealisationEquipmentResponseDtoConverter toRealisationEquipmentResponseDtoConverter;

  @Mock
  private RealisationEquipmentToFullRealisationEquipmentResponseDtoConverter toFullRealisationEquipmentResponseDtoConverter;

  @Mock
  private CreateRealisationEquipmentRequestDtoToJoinedCreateRealisationEquipmentRequestDtoConverter toJoinedCreateRealisationEquipmentRequestDtoConverter;

  @Mock
  private JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterToRealisationEquipmentConverter fromJoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterConverter;

  @Mock
  private JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterValidator joinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterValidator;

  @Mock
  private RealisationEquipmentWithUpdateRealisationEquipmentRequestDtoValidator realisationEquipmentWithUpdateRealisationEquipmentRequestDtoValidator;

  @Mock
  private RealisationEquipmentMapper realisationEquipmentMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private RealisationEquipmentRepository realisationEquipmentRepository;

  @Mock
  private RealisationService realisationService;

  @Nested
  @DisplayName("WHEN getRealisationEquipmentByRealisationIdAndEquipmentId is called")
  public class WHEN_getRealisationEquipmentByRealisationIdAndEquipmentId {

    @Test
    @DisplayName("GIVEN realisationId and attachmentId " +
      "... THEN RealisationEquipmentResponseDto is returned")
    public void GIVEN_ids_THEN_RealisationEquipmentResponseDto() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val attachmentId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationEquipmentRepository successfully finds
      val realisationEquipment = RealisationEquipmentMother.complete().build();
      given(realisationEquipmentRepository.findByRealisationIdAndEquipmentIdAndRealisationWorkUserId(same(realisationId), same(attachmentId), same(userId))).willReturn(Optional.of(realisationEquipment));
      // ... RealisationEquipmentToFullRealisationEquipmentResponseDtoConverter successfully converts
      val expectedResponseDto = FullRealisationEquipmentResponseDtoMother.complete().build();
      given(toFullRealisationEquipmentResponseDtoConverter.convert(same(realisationEquipment))).willReturn(expectedResponseDto);

      // WHEN
      // ... getRealisationEquipmentByRealisationIdAndEquipmentId is called
      val responseDto = realisationEquipmentService.getRealisationEquipmentByRealisationIdAndEquipmentId(realisationId, attachmentId);

      // THEN
      // ... FullRealisationEquipmentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createRealisationEquipmentForRealisationId is called")
  public class WHEN_createRealisationEquipmentForRealisationId {

    @Test
    @DisplayName("GIVEN realisationId and CreateRealisationEquipmentRequestDto " +
      "... THEN RealisationEquipmentResponseDto")
    public void GIVEN_LongAndCreateRealisationEquipmentRequestDto_THEN_RealisationEquipmentResponseDto() {
      // GIVEN
      // ... realisationId
      val realisationId = 1L;
      // ... CreateRealisationEquipmentRequestDto
      val requestDto = CreateRealisationEquipmentRequestDtoMother.complete().build();
      // ... RealisationService successfully gets Realisation by id
      val realisation = RealisationMother.complete().build();
      given(realisationService.getById(same(realisationId))).willReturn(realisation);
      // ... CreateRealisationEquipmentRequestDtoToJoinedCreateRealisationEquipmentRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedCreateRealisationEquipmentRequestDtoMother.complete().build();
      given(toJoinedCreateRealisationEquipmentRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterValidator successfully validates
      willDoNothing().given(joinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterValidator).validate(any(JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapter.class));
      // ... JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterToRealisationEquipmentConverter successfully converts
      val realisationEquipment = RealisationEquipmentMother.complete().build();
      given(fromJoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterConverter.convert(any(JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapter.class))).willReturn(realisationEquipment);
      // ... RealisationEquipmentRepository successfully saves
      given(realisationEquipmentRepository.save(same(realisationEquipment))).willReturn(realisationEquipment);
      // ... RealisationEquipmentToFullRealisationEquipmentResponseDtoConverter successfully converts
      val expectedResponseDto = FullRealisationEquipmentResponseDtoMother.complete().build();
      given(toFullRealisationEquipmentResponseDtoConverter.convert(same(realisationEquipment))).willReturn(expectedResponseDto);

      // WHEN
      // ... createRealisationEquipmentForRealisationId is called
      val responseDto = realisationEquipmentService.createRealisationEquipmentForRealisationId(realisationId, requestDto);

      // THEN
      // ... RealisationEquipmentResponseDto
      then(realisationEquipmentRepository).should(times(1)).save(same(realisationEquipment));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateRealisationEquipmentByRealisationIdAndEquipmentId is called")
  public class WHEN_updateRealisationEquipmentByRealisationIdAndEquipmentId {

    @Test
    @DisplayName("GIVEN realisationId, attachmentId, and UpdateRealisationEquipmentRequestDto " +
      "... THEN RealisationEquipmentResponseDto is returned")
    public void GIVEN_idsAndUpdateRealisationEquipmentRequestDto_THEN_RealisationEquipmentResponseDto() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val attachmentId = 1L;
      // ... UpdateRealisationEquipmentRequestDto
      val requestDto = UpdateRealisationEquipmentRequestDtoMother.complete().build();
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationEquipmentRepository successfully finds
      val realisationEquipment = RealisationEquipmentMother.complete().build();
      given(realisationEquipmentRepository.findByRealisationIdAndEquipmentIdAndRealisationWorkUserId(same(realisationId), same(attachmentId), same(userId))).willReturn(Optional.of(realisationEquipment));
      // ... RealisationEquipmentWithUpdateRealisationEquipmentRequestDtoValidator successfully validates
      willDoNothing().given(realisationEquipmentWithUpdateRealisationEquipmentRequestDtoValidator).validate(same(realisationEquipment), same(requestDto));
      // ... RealisationEquipmentMapper successfully partially updates RealisationEquipment with UpdateRealisationEquipmentRequestDto
      given(realisationEquipmentMapper.partialUpdate(same(realisationEquipment), same(requestDto))).willReturn(realisationEquipment);
      // ... RealisationEquipmentRepository successfully saves RealisationEquipment
      given(realisationEquipmentRepository.save(same(realisationEquipment))).willReturn(realisationEquipment);
      // ... RealisationEquipmentToFullRealisationEquipmentResponseDtoConverter successfully converts
      val expectedResponseDto = FullRealisationEquipmentResponseDtoMother.complete().build();
      given(toFullRealisationEquipmentResponseDtoConverter.convert(same(realisationEquipment))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateRealisationEquipmentByRealisationIdAndEquipmentId is called
      val responseDto = realisationEquipmentService.updateRealisationEquipmentByRealisationIdAndEquipmentId(realisationId, attachmentId, requestDto);

      // THEN
      // ... RealisationEquipmentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteRealisationEquipmentByRealisationIdAndEquipmentId is called")
  public class WHEN_deleteRealisationEquipmentByRealisationIdAndEquipmentId {

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
      // ... RealisationEquipmentRepository successfully finds
      val realisationEquipment = RealisationEquipmentMother.complete().build();
      given(realisationEquipmentRepository.findByRealisationIdAndEquipmentIdAndRealisationWorkUserId(same(realisationId), same(attachmentId), same(userId))).willReturn(Optional.of(realisationEquipment));
      // ... RealisationEquipmentRepository successfully deletes RealisationEquipment
      willDoNothing().given(realisationEquipmentRepository).delete(realisationEquipment);

      // WHEN
      // ... deleteRealisationEquipmentByRealisationIdAndEquipmentId is called
      realisationEquipmentService.deleteRealisationEquipmentByRealisationIdAndEquipmentId(realisationId, attachmentId);

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
      // ... RealisationEquipmentRepository fails to find
      given(realisationEquipmentRepository.findByRealisationIdAndEquipmentIdAndRealisationWorkUserId(same(realisationId), same(attachmentId), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> realisationEquipmentService.getByRealisationIdAndEquipmentId(realisationId, attachmentId));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN realisationId and attachmentId " +
      "... THEN RealisationEquipment is returned")
    public void GIVEN_ids_THEN_RealisationEquipment() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val attachmentId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationEquipmentRepository successfully finds
      val expectedRealisationEquipment = RealisationEquipmentMother.complete().build();
      given(realisationEquipmentRepository.findByRealisationIdAndEquipmentIdAndRealisationWorkUserId(same(realisationId), same(attachmentId), same(userId))).willReturn(Optional.of(expectedRealisationEquipment));

      // WHEN
      // ... getById is called
      val returnedRealisationEquipment = realisationEquipmentService.getByRealisationIdAndEquipmentId(realisationId, attachmentId);

      // THEN
      // ... RealisationEquipmentResponseDto is returned
      and.then(returnedRealisationEquipment)
        .isNotNull()
        .isEqualTo(expectedRealisationEquipment);
    }

  }

}