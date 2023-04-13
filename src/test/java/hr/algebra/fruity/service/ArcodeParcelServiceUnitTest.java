package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.FullArcodeParcelResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.ArcodeParcelMapper;
import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.repository.ArcodeParcelRepository;
import hr.algebra.fruity.service.impl.CurrentUserArcodeParcelService;
import hr.algebra.fruity.utils.mother.dto.CreateArcodeParcelRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullArcodeParcelResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateArcodeParcelRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.ArcodeParcelMother;
import hr.algebra.fruity.utils.mother.model.UserMother;
import hr.algebra.fruity.validator.ArcodeParcelWithUpdateArcodeParcelRequestDtoValidator;
import hr.algebra.fruity.validator.CreateArcodeParcelRequestDtoValidator;
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
@DisplayName("ArcodeParcel Unit Test")
@SuppressWarnings("static-access")
public class ArcodeParcelServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserArcodeParcelService arcodeParcelService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private CreateArcodeParcelRequestDtoValidator createArcodeParcelRequestDtoValidator;

  @Mock
  private ArcodeParcelWithUpdateArcodeParcelRequestDtoValidator arcodeParcelWithUpdateArcodeParcelRequestDtoValidator;

  @Mock
  private ArcodeParcelMapper arcodeParcelMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private ArcodeParcelRepository arcodeParcelRepository;

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
      val arcodeParcel = ArcodeParcelMother.complete().build();
      given(arcodeParcelRepository.findById(same(id))).willReturn(Optional.of(arcodeParcel));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = arcodeParcel.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... ConversionService successfully converts from User to FullArcodeParcelResponseDto
      val expectedResponseDto = FullArcodeParcelResponseDtoMother.complete().build();
      given(conversionService.convert(same(arcodeParcel), same(FullArcodeParcelResponseDto.class))).willReturn(expectedResponseDto);

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
      // CreateArcodeParcelRequestDtoValidator successfully validates CreateArcodeParcelRequestDto
      willDoNothing().given(createArcodeParcelRequestDtoValidator).validate(same(requestDto));
      // ... ConversionService successfully converts from CreateArcodeParcelRequestDto to ArcodeParcel
      val arcodeParcel = ArcodeParcelMother.complete().build();
      given(conversionService.convert(same(requestDto), same(ArcodeParcel.class))).willReturn(arcodeParcel);
      // ... ArcodeParcelRepository will successfully save ArcodeParcel
      given(arcodeParcelRepository.save(same(arcodeParcel))).willReturn(arcodeParcel);
      // ... ConversionService successfully converts from ArcodeParcel to FullArcodeParcelResponseDto
      val expectedResponseDto = FullArcodeParcelResponseDtoMother.complete().build();
      given(conversionService.convert(same(arcodeParcel), same(FullArcodeParcelResponseDto.class))).willReturn(expectedResponseDto);

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
      val arcodeParcel = ArcodeParcelMother.complete().build();
      given(arcodeParcelRepository.findById(same(id))).willReturn(Optional.of(arcodeParcel));
      // ... UpdateArcodeParcelRequestDto
      val requestDto = UpdateArcodeParcelRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = arcodeParcel.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... ArcodeParcelWithUpdateArcodeParcelRequestDtoValidator successfully validates ArcodeParcel with UpdateArcodeParcelRequestDto
      willDoNothing().given(arcodeParcelWithUpdateArcodeParcelRequestDtoValidator).validate(same(arcodeParcel), same(requestDto));
      // ... ArcodeParcelMapper successfully partially updates ArcodeParcel with UpdateArcodeParcelRequestDto
      given(arcodeParcelMapper.partialUpdate(same(arcodeParcel), same(requestDto))).willReturn(arcodeParcel);
      // ... ArcodeParcelRepository successfully saves ArcodeParcel
      given(arcodeParcelRepository.save(same(arcodeParcel))).willReturn(arcodeParcel);
      // ... ConversionService successfully converts from ArcodeParcel to FullArcodeParcelResponseDto
      val expectedResponseDto = FullArcodeParcelResponseDtoMother.complete().build();
      given(conversionService.convert(same(arcodeParcel), same(FullArcodeParcelResponseDto.class))).willReturn(expectedResponseDto);

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
      val arcodeParcel = ArcodeParcelMother.complete().build();
      given(arcodeParcelRepository.findById(same(id))).willReturn(Optional.of(arcodeParcel));
      // ... CurrentUserService's logged-in User is not equal to ArcodeParcel User
      val loggedInUser = arcodeParcel.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... ArcodeParcelRepository successfully deletes ArcodeParcel
      willDoNothing().given(arcodeParcelRepository).delete(arcodeParcel);

      // WHEN
      arcodeParcelService.deleteArcodeParcelById(id);

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
      given(arcodeParcelRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> arcodeParcelService.getById(id));

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
      val arcodeParcel = ArcodeParcelMother.complete().build();
      given(arcodeParcelRepository.findById(same(id))).willReturn(Optional.of(arcodeParcel));
      // ... CurrentUserService's logged-in User is not equal to ArcodeParcel User
      val loggedInUser = UserMother.complete().id(arcodeParcel.getUser().getId() + 1).build();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getById is called
      when(() -> arcodeParcelService.getById(id));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN ArcodeParcel is returned")
    public void GIVEN_id_THEN_ArcodeParcel() {
      // GIVEN
      // ... id
      val id = 1L;
      val expectedArcodeParcel = ArcodeParcelMother.complete().build();
      given(arcodeParcelRepository.findById(same(id))).willReturn(Optional.of(expectedArcodeParcel));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = expectedArcodeParcel.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

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