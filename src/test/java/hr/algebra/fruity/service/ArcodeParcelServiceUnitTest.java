package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.ArcodeParcelToArcodeParcelResponseDtoConverter;
import hr.algebra.fruity.converter.ArcodeParcelToFullArcodeParcelResponseDtoConverter;
import hr.algebra.fruity.converter.CreateArcodeParcelRequestDtoToJoinedCreateArcodeParcelRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateArcodeParcelRequestDtoToArcodeParcelConverter;
import hr.algebra.fruity.converter.RowClusterToRowClusterResponseDtoConverter;
import hr.algebra.fruity.converter.UpdateArcodeParcelRequestDtoToJoinedUpdateArcodeParcelRequestDtoConverter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.ArcodeParcelMapper;
import hr.algebra.fruity.repository.ArcodeParcelRepository;
import hr.algebra.fruity.repository.RowClusterRepository;
import hr.algebra.fruity.service.impl.CurrentUserArcodeParcelService;
import hr.algebra.fruity.utils.mother.dto.CreateArcodeParcelRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullArcodeParcelResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedCreateArcodeParcelRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedUpdateArcodeParcelRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateArcodeParcelRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.ArcodeParcelMother;
import hr.algebra.fruity.validator.ArcodeParcelWithJoinedUpdateArcodeParcelRequestDtoValidator;
import hr.algebra.fruity.validator.JoinedCreateArcodeParcelRequestDtoValidator;
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
@DisplayName("ArcodeParcel Unit Test")
@SuppressWarnings("static-access")
public class ArcodeParcelServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserArcodeParcelService arcodeParcelService;

  @Mock
  private ArcodeParcelToArcodeParcelResponseDtoConverter toArcodeParcelResponseDtoConverter;

  @Mock
  private ArcodeParcelToFullArcodeParcelResponseDtoConverter toFullArcodeParcelResponseDtoConverter;

  @Mock
  private CreateArcodeParcelRequestDtoToJoinedCreateArcodeParcelRequestDtoConverter toJoinedCreateArcodeParcelRequestDtoConverter;

  @Mock
  private JoinedCreateArcodeParcelRequestDtoToArcodeParcelConverter fromJoinedCreateArcodeParcelRequestDtoConverter;

  @Mock
  private UpdateArcodeParcelRequestDtoToJoinedUpdateArcodeParcelRequestDtoConverter toJoinedUpdateArcodeParcelRequestDtoConverter;

  @Mock
  private RowClusterToRowClusterResponseDtoConverter toRowClusterResponseDtoConverter;

  @Mock
  private JoinedCreateArcodeParcelRequestDtoValidator joinedCreateArcodeParcelRequestDtoValidator;

  @Mock
  private ArcodeParcelWithJoinedUpdateArcodeParcelRequestDtoValidator arcodeParcelWithJoinedUpdateArcodeParcelRequestDtoValidator;

  @Mock
  private ArcodeParcelMapper arcodeParcelMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private ArcodeParcelRepository arcodeParcelRepository;

  @Mock
  private RowClusterRepository rowClusterRepository;

  @Nested
  @DisplayName("WHEN getArcodeParcelById is called")
  public class WHEN_getArcodeParcelById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN FullArcodeParcelResponseDto is returned")
    public void GIVEN_id_THEN_FullArcodeParcelResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... ArcodeParcelRepository fails to findByIdAndUserId
      val arcodeParcel = ArcodeParcelMother.complete().build();
      given(arcodeParcelRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(arcodeParcel));
      // ... ArcodeParcelToFullArcodeParcelResponseDtoConverter successfully converts
      val expectedResponseDto = FullArcodeParcelResponseDtoMother.complete().build();
      given(toFullArcodeParcelResponseDtoConverter.convert(same(arcodeParcel))).willReturn(expectedResponseDto);

      // WHEN
      // ... getArcodeParcelById is called
      val responseDto = arcodeParcelService.getArcodeParcelById(id);

      // THEN
      // ... ArcodeParcelResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createArcodeParcel is called")
  public class WHEN_createArcodeParcel {

    @Test
    @DisplayName("GIVEN CreateArcodeParcelRequestDto " +
      "... THEN FullArcodeParcelResponseDto")
    public void GIVEN_CreateArcodeParcelRequestDto_THEN_FullArcodeParcelResponseDto() {
      // GIVEN
      // ... CreateArcodeParcelRequestDto
      val requestDto = CreateArcodeParcelRequestDtoMother.complete().build();
      // ... CreateArcodeParcelRequestDtoToJoinedCreateArcodeParcelRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedCreateArcodeParcelRequestDtoMother.complete().build();
      given(toJoinedCreateArcodeParcelRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... JoinedCreateArcodeParcelRequestDtoValidator successfully validates
      willDoNothing().given(joinedCreateArcodeParcelRequestDtoValidator).validate(same(joinedRequestDto));
      // ... JoinedArcodeParcelRequestDtoToArcodeParcelConverter successfully converts
      val arcodeParcel = ArcodeParcelMother.complete().build();
      given(fromJoinedCreateArcodeParcelRequestDtoConverter.convert(same(joinedRequestDto))).willReturn(arcodeParcel);
      // ... ArcodeParcelRepository successfully saves
      given(arcodeParcelRepository.save(same(arcodeParcel))).willReturn(arcodeParcel);
      // ... ArcodeParcelToFullArcodeParcelResponseDtoConverter successfully converts
      val expectedResponseDto = FullArcodeParcelResponseDtoMother.complete().build();
      given(toFullArcodeParcelResponseDtoConverter.convert(same(arcodeParcel))).willReturn(expectedResponseDto);

      // WHEN
      // ... createArcodeParcel is called
      val responseDto = arcodeParcelService.createArcodeParcel(requestDto);

      // THEN
      // ... ArcodeParcelResponseDto
      then(arcodeParcelRepository).should(times(1)).save(same(arcodeParcel));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateArcodeParcelById is called")
  public class WHEN_updateArcodeParcelById {

    @Test
    @DisplayName("GIVEN id and UpdateArcodeParcelRequestDto " +
      "... THEN FullArcodeParcelResponseDto is returned")
    public void GIVEN_idAndUpdateArcodeParcelRequestDto_THEN_FullArcodeParcelResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... ArcodeParcelRepository fails to findByIdAndUserId
      val arcodeParcel = ArcodeParcelMother.complete().build();
      given(arcodeParcelRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(arcodeParcel));
      // ... UpdateArcodeParcelRequestDto
      val requestDto = UpdateArcodeParcelRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = arcodeParcel.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... UpdateArcodeParcelRequestDtoToJoinedUpdateArcodeParcelRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedUpdateArcodeParcelRequestDtoMother.complete().build();
      given(toJoinedUpdateArcodeParcelRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... ArcodeParcelWithJoinedUpdateArcodeParcelRequestDtoValidator successfully validates
      willDoNothing().given(arcodeParcelWithJoinedUpdateArcodeParcelRequestDtoValidator).validate(same(arcodeParcel), same(joinedRequestDto));
      // ... ArcodeParcelMapper successfully partially updates
      given(arcodeParcelMapper.partialUpdate(same(arcodeParcel), same(joinedRequestDto))).willReturn(arcodeParcel);
      // ... ArcodeParcelRepository successfully saves ArcodeParcel
      given(arcodeParcelRepository.save(same(arcodeParcel))).willReturn(arcodeParcel);
      // ... ArcodeParcelToFullArcodeParcelResponseDtoConverter successfully converts
      val expectedResponseDto = FullArcodeParcelResponseDtoMother.complete().build();
      given(toFullArcodeParcelResponseDtoConverter.convert(same(arcodeParcel))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateArcodeParcelById is called
      val responseDto = arcodeParcelService.updateArcodeParcelById(id, requestDto);

      // THEN
      // ... ArcodeParcelResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteArcodeParcelById is called")
  public class WHEN_deleteArcodeParcelById {

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
      // ... ArcodeParcelRepository fails to findByIdAndUserId
      val arcodeParcel = ArcodeParcelMother.complete().build();
      given(arcodeParcelRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(arcodeParcel));
      // ... CurrentUserService's logged-in User is not equal to ArcodeParcel User
      val loggedInUser = arcodeParcel.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... ArcodeParcelRepository successfully deletes
      willDoNothing().given(arcodeParcelRepository).delete(arcodeParcel);

      // WHEN
      // ... deleteArcodeParcelById is called
      arcodeParcelService.deleteArcodeParcelById(id);

      // THEN
      // ... void
    }

  }

  @Nested
  @DisplayName("WHEN getById is called")
  public class WHEN_getById {

    @Test
    @DisplayName("GIVEN invalid id or userId " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidIdOrUserId_THEN_EntityNotFoundException() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... ArcodeParcelRepository fails to findByIdAndUserId
      given(arcodeParcelRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> arcodeParcelService.getById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN ArcodeParcel is returned")
    public void GIVEN_id_THEN_ArcodeParcel() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... ArcodeParcelRepository fails to findByIdAndUserId
      val expectedArcodeParcel = ArcodeParcelMother.complete().build();
      given(arcodeParcelRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(expectedArcodeParcel));

      // WHEN
      // ... getById is called
      val returnedArcodeParcel = arcodeParcelService.getById(id);

      // THEN
      // ... ArcodeParcelResponseDto is returned
      and.then(returnedArcodeParcel)
        .isNotNull()
        .isEqualTo(expectedArcodeParcel);
    }

  }

}