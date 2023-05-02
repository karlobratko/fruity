package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.CreateRealisationHarvestRequestDtoToJoinedCreateRealisationHarvestRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapterToRealisationHarvestConverter;
import hr.algebra.fruity.converter.RealisationHarvestToFullRealisationHarvestResponseDtoConverter;
import hr.algebra.fruity.converter.RealisationHarvestToRealisationHarvestResponseDtoConverter;
import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.RealisationHarvestMapper;
import hr.algebra.fruity.repository.RealisationHarvestRepository;
import hr.algebra.fruity.service.impl.CurrentUserRealisationHarvestService;
import hr.algebra.fruity.utils.mother.dto.CreateRealisationHarvestRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullRealisationHarvestResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedCreateRealisationHarvestRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateRealisationHarvestRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.RealisationHarvestMother;
import hr.algebra.fruity.utils.mother.model.RealisationMother;
import hr.algebra.fruity.validator.JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapterValidator;
import hr.algebra.fruity.validator.RealisationHarvestWithUpdateRealisationHarvestRequestDtoValidator;
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
@DisplayName("RealisationHarvest Service Unit Test")
@SuppressWarnings("static-access")
public class RealisationHarvestServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserRealisationHarvestService realisationHarvestService;

  @Mock
  private RealisationHarvestToRealisationHarvestResponseDtoConverter toRealisationHarvestResponseDtoConverter;

  @Mock
  private RealisationHarvestToFullRealisationHarvestResponseDtoConverter toFullRealisationHarvestResponseDtoConverter;

  @Mock
  private CreateRealisationHarvestRequestDtoToJoinedCreateRealisationHarvestRequestDtoConverter toJoinedCreateRealisationHarvestRequestDtoConverter;

  @Mock
  private JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapterToRealisationHarvestConverter fromJoinedCreateRealisationHarvestRequestDtoWithRealisationAdapterConverter;

  @Mock
  private JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapterValidator joinedCreateRealisationHarvestRequestDtoWithRealisationAdapterValidator;

  @Mock
  private RealisationHarvestWithUpdateRealisationHarvestRequestDtoValidator realisationHarvestWithUpdateRealisationHarvestRequestDtoValidator;

  @Mock
  private RealisationHarvestMapper realisationHarvestMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private RealisationHarvestRepository realisationHarvestRepository;

  @Mock
  private RealisationService realisationService;

  @Nested
  @DisplayName("WHEN getRealisationHarvestByRealisationIdAndHarvestId is called")
  public class WHEN_getRealisationHarvestByRealisationIdAndHarvestId {

    @Test
    @DisplayName("GIVEN realisationId and fruitCultivarId " +
      "... THEN RealisationHarvestResponseDto is returned")
    public void GIVEN_ids_THEN_RealisationHarvestResponseDto() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val fruitCultivarId = 1;
      val harvestedClassId = 1;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationHarvestRepository successfully finds
      val realisationHarvest = RealisationHarvestMother.complete().build();
      given(realisationHarvestRepository.findByRealisationIdAndFruitCultivarIdAndHarvestedFruitClassIdAndRealisationWorkUserId(same(realisationId), same(fruitCultivarId), same(harvestedClassId), same(userId))).willReturn(Optional.of(realisationHarvest));
      // ... RealisationHarvestToFullRealisationHarvestResponseDtoConverter successfully converts
      val expectedResponseDto = FullRealisationHarvestResponseDtoMother.complete().build();
      given(toFullRealisationHarvestResponseDtoConverter.convert(same(realisationHarvest))).willReturn(expectedResponseDto);

      // WHEN
      // ... getRealisationHarvestByRealisationIdAndHarvestId is called
      val responseDto = realisationHarvestService.getRealisationHarvestByRealisationIdAndFruitCultivarIdAndClassId(realisationId, fruitCultivarId, harvestedClassId);

      // THEN
      // ... FullRealisationHarvestResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createRealisationHarvestForRealisationId is called")
  public class WHEN_createRealisationHarvestForRealisationId {

    @Test
    @DisplayName("GIVEN realisationId and CreateRealisationHarvestRequestDto " +
      "... THEN RealisationHarvestResponseDto")
    public void GIVEN_LongAndCreateRealisationHarvestRequestDto_THEN_RealisationHarvestResponseDto() {
      // GIVEN
      // ... realisationId
      val realisationId = 1L;
      // ... CreateRealisationHarvestRequestDto
      val requestDto = CreateRealisationHarvestRequestDtoMother.complete().build();
      // ... RealisationService successfully gets Realisation by id
      val realisation = RealisationMother.complete().build();
      given(realisationService.getById(same(realisationId))).willReturn(realisation);
      // ... CreateRealisationHarvestRequestDtoToJoinedCreateRealisationHarvestRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedCreateRealisationHarvestRequestDtoMother.complete().build();
      given(toJoinedCreateRealisationHarvestRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapterValidator successfully validates
      willDoNothing().given(joinedCreateRealisationHarvestRequestDtoWithRealisationAdapterValidator).validate(any(JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapter.class));
      // ... JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapterToRealisationHarvestConverter successfully converts
      val realisationHarvest = RealisationHarvestMother.complete().build();
      given(fromJoinedCreateRealisationHarvestRequestDtoWithRealisationAdapterConverter.convert(any(JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapter.class))).willReturn(realisationHarvest);
      // ... RealisationHarvestRepository successfully saves
      given(realisationHarvestRepository.save(same(realisationHarvest))).willReturn(realisationHarvest);
      // ... RealisationHarvestToFullRealisationHarvestResponseDtoConverter successfully converts
      val expectedResponseDto = FullRealisationHarvestResponseDtoMother.complete().build();
      given(toFullRealisationHarvestResponseDtoConverter.convert(same(realisationHarvest))).willReturn(expectedResponseDto);

      // WHEN
      // ... createRealisationHarvestForRealisationId is called
      val responseDto = realisationHarvestService.createRealisationHarvestForRealisationId(realisationId, requestDto);

      // THEN
      // ... RealisationHarvestResponseDto
      then(realisationHarvestRepository).should(times(1)).save(same(realisationHarvest));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateRealisationHarvestByRealisationIdAndHarvestId is called")
  public class WHEN_updateRealisationHarvestByRealisationIdAndHarvestId {

    @Test
    @DisplayName("GIVEN realisationId, fruitCultivarId, and UpdateRealisationHarvestRequestDto " +
      "... THEN RealisationHarvestResponseDto is returned")
    public void GIVEN_idsAndUpdateRealisationHarvestRequestDto_THEN_RealisationHarvestResponseDto() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val fruitCultivarId = 1;
      val harvestedClassId = 1;
      // ... UpdateRealisationHarvestRequestDto
      val requestDto = UpdateRealisationHarvestRequestDtoMother.complete().build();
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationHarvestRepository successfully finds
      val realisationHarvest = RealisationHarvestMother.complete().build();
      given(realisationHarvestRepository.findByRealisationIdAndFruitCultivarIdAndHarvestedFruitClassIdAndRealisationWorkUserId(same(realisationId), same(fruitCultivarId), same(harvestedClassId), same(userId))).willReturn(Optional.of(realisationHarvest));
      // ... RealisationHarvestWithUpdateRealisationHarvestRequestDtoValidator successfully validates
      willDoNothing().given(realisationHarvestWithUpdateRealisationHarvestRequestDtoValidator).validate(same(realisationHarvest), same(requestDto));
      // ... RealisationHarvestMapper successfully partially updates RealisationHarvest with UpdateRealisationHarvestRequestDto
      given(realisationHarvestMapper.partialUpdate(same(realisationHarvest), same(requestDto))).willReturn(realisationHarvest);
      // ... RealisationHarvestRepository successfully saves RealisationHarvest
      given(realisationHarvestRepository.save(same(realisationHarvest))).willReturn(realisationHarvest);
      // ... RealisationHarvestToFullRealisationHarvestResponseDtoConverter successfully converts
      val expectedResponseDto = FullRealisationHarvestResponseDtoMother.complete().build();
      given(toFullRealisationHarvestResponseDtoConverter.convert(same(realisationHarvest))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateRealisationHarvestByRealisationIdAndHarvestId is called
      val responseDto = realisationHarvestService.updateRealisationHarvestByRealisationIdAndFruitCultivarIdAndClassId(realisationId, fruitCultivarId, harvestedClassId, requestDto);

      // THEN
      // ... RealisationHarvestResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteRealisationHarvestByRealisationIdAndHarvestId is called")
  public class WHEN_deleteRealisationHarvestByRealisationIdAndHarvestId {

    @Test
    @DisplayName("GIVEN realisationId and fruitCultivarId " +
      "... THEN void")
    public void GIVEN_ids_THEN_void() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val fruitCultivarId = 1;
      val harvestedClassId = 1;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationHarvestRepository successfully finds
      val realisationHarvest = RealisationHarvestMother.complete().build();
      given(realisationHarvestRepository.findByRealisationIdAndFruitCultivarIdAndHarvestedFruitClassIdAndRealisationWorkUserId(same(realisationId), same(fruitCultivarId), same(harvestedClassId), same(userId))).willReturn(Optional.of(realisationHarvest));
      // ... RealisationHarvestRepository successfully deletes RealisationHarvest
      willDoNothing().given(realisationHarvestRepository).delete(realisationHarvest);

      // WHEN
      // ... deleteRealisationHarvestByRealisationIdAndHarvestId is called
      realisationHarvestService.deleteRealisationHarvestByRealisationIdAndFruitCultivarIdAndClassId(realisationId, fruitCultivarId, harvestedClassId);

      // THEN
      // ... void
    }

  }

  @Nested
  @DisplayName("WHEN getById is called")
  public class WHEN_getById {

    @Test
    @DisplayName("GIVEN invalid realisationId and fruitCultivarId " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidIds_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val fruitCultivarId = 1;
      val harvestedClassId = 1;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationHarvestRepository fails to find
      given(realisationHarvestRepository.findByRealisationIdAndFruitCultivarIdAndHarvestedFruitClassIdAndRealisationWorkUserId(same(realisationId), same(fruitCultivarId), same(harvestedClassId), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> realisationHarvestService.getByRealisationIdAndFruitCultivarIdAndClassId(realisationId, fruitCultivarId, harvestedClassId));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN realisationId and fruitCultivarId " +
      "... THEN RealisationHarvest is returned")
    public void GIVEN_ids_THEN_RealisationHarvest() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val fruitCultivarId = 1;
      val harvestedClassId = 1;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationHarvestRepository successfully finds
      val expectedRealisationHarvest = RealisationHarvestMother.complete().build();
      given(realisationHarvestRepository.findByRealisationIdAndFruitCultivarIdAndHarvestedFruitClassIdAndRealisationWorkUserId(same(realisationId), same(fruitCultivarId), same(harvestedClassId), same(userId))).willReturn(Optional.of(expectedRealisationHarvest));

      // WHEN
      // ... getById is called
      val returnedRealisationHarvest = realisationHarvestService.getByRealisationIdAndFruitCultivarIdAndClassId(realisationId, fruitCultivarId, harvestedClassId);

      // THEN
      // ... RealisationHarvestResponseDto is returned
      and.then(returnedRealisationHarvest)
        .isNotNull()
        .isEqualTo(expectedRealisationHarvest);
    }

  }

}