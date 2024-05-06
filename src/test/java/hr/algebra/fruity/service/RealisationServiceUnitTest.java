package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.CreateRealisationRequestDtoToJoinedCreateRealisationRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateRealisationRequestDtoToRealisationConverter;
import hr.algebra.fruity.converter.RealisationToFullRealisationResponseDtoConverter;
import hr.algebra.fruity.converter.RealisationToRealisationResponseDtoConverter;
import hr.algebra.fruity.converter.UpdateRealisationRequestDtoToJoinedUpdateRealisationRequestDtoConverter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.RealisationMapper;
import hr.algebra.fruity.repository.RealisationRepository;
import hr.algebra.fruity.service.impl.CurrentUserRealisationService;
import hr.algebra.fruity.utils.mother.dto.CreateRealisationRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullRealisationResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedCreateRealisationRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedUpdateRealisationRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateRealisationRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.RealisationMother;
import hr.algebra.fruity.validator.JoinedCreateRealisationRequestDtoValidator;
import hr.algebra.fruity.validator.RealisationWithJoinedUpdateRealisationRequestDtoValidator;
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
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("Realisation Service Unit Test")
@SuppressWarnings("static-access")
public class RealisationServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserRealisationService realisationService;

  @Mock
  private RealisationToRealisationResponseDtoConverter toRealisationResponseDtoConverter;

  @Mock
  private RealisationToFullRealisationResponseDtoConverter toFullRealisationResponseDtoConverter;

  @Mock
  private CreateRealisationRequestDtoToJoinedCreateRealisationRequestDtoConverter toJoinedCreateRealisationRequestDtoConverter;

  @Mock
  private JoinedCreateRealisationRequestDtoToRealisationConverter fromJoinedCreateRealisationRequestDtoConverter;

  @Mock
  private UpdateRealisationRequestDtoToJoinedUpdateRealisationRequestDtoConverter toJoinedUpdateRealisationRequestDtoConverter;

  @Mock
  private JoinedCreateRealisationRequestDtoValidator joinedCreateRealisationRequestDtoValidator;

  @Mock
  private RealisationWithJoinedUpdateRealisationRequestDtoValidator realisationWithJoinedUpdateRealisationRequestDtoValidator;

  @Mock
  private RealisationMapper realisationMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private RealisationRepository realisationRepository;

  @Nested
  @DisplayName("WHEN getRealisationById is called")
  public class WHEN_getRealisationById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN RealisationResponseDto is returned")
    public void GIVEN_id_THEN_RealisationResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationRepository fails to findByIdAndWorkUserId
      val realisation = RealisationMother.complete().build();
      given(realisationRepository.findByIdAndWorkUserId(same(id), same(userId))).willReturn(Optional.of(realisation));
      // ... RealisationToFullRealisationResponseDtoConverter successfully converts
      val expectedResponseDto = FullRealisationResponseDtoMother.complete().build();
      given(toFullRealisationResponseDtoConverter.convert(same(realisation))).willReturn(expectedResponseDto);

      // WHEN
      // ... getRealisationById is called
      val responseDto = realisationService.getRealisationById(id);

      // THEN
      // ... FullRealisationResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createRealisation is called")
  public class WHEN_createRealisation {

    @Test
    @DisplayName("GIVEN CreateRealisationRequestDto " +
      "... THEN RealisationResponseDto")
    public void GIVEN_CreateRealisationRequestDto_THEN_RealisationResponseDto() {
      // GIVEN
      // ... CreateRealisationRequestDto
      val requestDto = CreateRealisationRequestDtoMother.complete().build();
      // ... CreateRealisationRequestDtoToJoinedCreateRealisationRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedCreateRealisationRequestDtoMother.complete().build();
      given(toJoinedCreateRealisationRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // CreateRealisationRequestDtoValidator successfully validates
      willDoNothing().given(joinedCreateRealisationRequestDtoValidator).validate(same(joinedRequestDto));
      // ... JoinedCreateRealisationRequestDtoToRealisationConverter successfully converts
      val realisation = RealisationMother.complete().build();
      given(fromJoinedCreateRealisationRequestDtoConverter.convert(same(joinedRequestDto))).willReturn(realisation);
      // ... RealisationRepository successfully saves
      given(realisationRepository.save(same(realisation))).willReturn(realisation);
      // ... RealisationToFullRealisationResponseDtoConverter successfully converts
      val expectedResponseDto = FullRealisationResponseDtoMother.complete().build();
      given(toFullRealisationResponseDtoConverter.convert(same(realisation))).willReturn(expectedResponseDto);

      // WHEN
      // ... createRealisation is called
      val responseDto = realisationService.createRealisation(requestDto);

      // THEN
      // ... RealisationResponseDto
      then(realisationRepository).should(times(1)).save(same(realisation));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateRealisationById is called")
  public class WHEN_updateRealisationById {

    @Test
    @DisplayName("GIVEN id and UpdateRealisationRequestDto " +
      "... THEN RealisationResponseDto is returned")
    public void GIVEN_idAndUpdateRealisationRequestDto_THEN_RealisationResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... UpdateRealisationRequestDto
      val requestDto = UpdateRealisationRequestDtoMother.complete().build();
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationRepository successfully finds
      val realisation = RealisationMother.complete().build();
      given(realisationRepository.findByIdAndWorkUserId(same(id), same(userId))).willReturn(Optional.of(realisation));
      // ... UpdateRealisationRequestDtoToJoinedUpdateRealisationRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedUpdateRealisationRequestDtoMother.complete().build();
      given(toJoinedUpdateRealisationRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... RealisationWithUpdateRealisationRequestDtoValidator successfully validates Realisation with UpdateRealisationRequestDto
      willDoNothing().given(realisationWithJoinedUpdateRealisationRequestDtoValidator).validate(same(realisation), same(joinedRequestDto));
      // ... RealisationMapper successfully partially updates Realisation with UpdateRealisationRequestDto
      given(realisationMapper.partialUpdate(same(realisation), same(joinedRequestDto))).willReturn(realisation);
      // ... RealisationRepository successfully saves Realisation
      given(realisationRepository.save(same(realisation))).willReturn(realisation);
      // ... RealisationToFullRealisationResponseDtoConverter successfully converts
      val expectedResponseDto = FullRealisationResponseDtoMother.complete().build();
      given(toFullRealisationResponseDtoConverter.convert(same(realisation))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateRealisationById is called
      val responseDto = realisationService.updateRealisationById(id, requestDto);

      // THEN
      // ... RealisationResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteRealisationById is called")
  public class WHEN_deleteRealisationById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN void")
    public void GIVEN_id_THEN_void() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationRepository successfully finds
      val realisation = RealisationMother.complete().build();
      given(realisationRepository.findByIdAndWorkUserId(same(id), same(userId))).willReturn(Optional.of(realisation));
      // ... RealisationRepository successfully deletes Realisation
      willDoNothing().given(realisationRepository).delete(realisation);

      // WHEN
      realisationService.deleteRealisationById(id);

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
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationRepository fails to findByIdAndWorkUserId
      given(realisationRepository.findByIdAndWorkUserId(same(id), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> realisationService.getById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN Realisation is returned")
    public void GIVEN_id_THEN_Realisation() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationRepository fails to findByIdAndWorkUserId
      val expectedRealisation = RealisationMother.complete().build();
      given(realisationRepository.findByIdAndWorkUserId(same(id), same(userId))).willReturn(Optional.of(expectedRealisation));

      // WHEN
      // ... getById is called
      val returnedRealisation = realisationService.getById(id);

      // THEN
      // ... RealisationResponseDto is returned
      and.then(returnedRealisation)
        .isNotNull()
        .isEqualTo(expectedRealisation);
    }

  }

}