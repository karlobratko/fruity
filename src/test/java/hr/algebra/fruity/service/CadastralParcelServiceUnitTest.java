package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.FullCadastralParcelResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.CadastralParcelMapper;
import hr.algebra.fruity.model.CadastralParcel;
import hr.algebra.fruity.repository.CadastralParcelRepository;
import hr.algebra.fruity.service.impl.CurrentUserCadastralParcelService;
import hr.algebra.fruity.utils.mother.dto.CreateCadastralParcelRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullCadastralParcelResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateCadastralParcelRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.CadastralParcelMother;
import hr.algebra.fruity.utils.mother.model.UserMother;
import hr.algebra.fruity.validator.CadastralParcelWithUpdateCadastralParcelRequestDtoValidator;
import hr.algebra.fruity.validator.CreateCadastralParcelRequestDtoValidator;
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
@DisplayName("CadastralParcel Unit Test")
@SuppressWarnings("static-access")
public class CadastralParcelServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserCadastralParcelService cadastralParcelService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private CreateCadastralParcelRequestDtoValidator createCadastralParcelRequestDtoValidator;

  @Mock
  private CadastralParcelWithUpdateCadastralParcelRequestDtoValidator cadastralParcelWithUpdateCadastralParcelRequestDtoValidator;

  @Mock
  private CadastralParcelMapper cadastralParcelMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private CadastralParcelRepository cadastralParcelRepository;

  @Nested
  @DisplayName("WHEN getCadastralParcelById is called")
  public class WHEN_getCadastralParcelById {

    @Test
    @DisplayName("GIVEN invalid id " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidId_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid id
      val id = 1L;
      given(cadastralParcelRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getCadastralParcelById is called
      when(() -> cadastralParcelService.getCadastralParcelById(id));

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
      val cadastralParcel = CadastralParcelMother.complete().build();
      given(cadastralParcelRepository.findById(same(id))).willReturn(Optional.of(cadastralParcel));
      // ... CurrentUserService's logged-in User is not equal to CadastralParcel User
      val loggedInUser = UserMother.complete().id(cadastralParcel.getUser().getId() + 1).build();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getCadastralParcelById is called
      when(() -> cadastralParcelService.getCadastralParcelById(id));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN FullCadastralParcelResponseDto is returned")
    public void GIVEN_id_THEN_FullCadastralParcelResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      val cadastralParcel = CadastralParcelMother.complete().build();
      given(cadastralParcelRepository.findById(same(id))).willReturn(Optional.of(cadastralParcel));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = cadastralParcel.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... ConversionService successfully converts from User to FullCadastralParcelResponseDto
      val expectedResponseDto = FullCadastralParcelResponseDtoMother.complete().build();
      given(conversionService.convert(same(cadastralParcel), same(FullCadastralParcelResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... getCadastralParcelById is called
      val responseDto = cadastralParcelService.getCadastralParcelById(id);

      // THEN
      // ... CadastralParcelResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createCadastralParcel is called")
  public class WHEN_createCadastralParcel {

    @Test
    @DisplayName("GIVEN CreateCadastralParcelRequestDto " +
      "... THEN FullCadastralParcelResponseDto")
    public void GIVEN_CreateCadastralParcelRequestDto_THEN_FullCadastralParcelResponseDto() {
      // GIVEN
      // ... CreateCadastralParcelRequestDto
      val requestDto = CreateCadastralParcelRequestDtoMother.complete().build();
      // CreateCadastralParcelRequestDtoValidator successfully validates CreateCadastralParcelRequestDto
      willDoNothing().given(createCadastralParcelRequestDtoValidator).validate(same(requestDto));
      // ... ConversionService successfully converts from CreateCadastralParcelRequestDto to CadastralParcel
      val cadastralParcel = CadastralParcelMother.complete().build();
      given(conversionService.convert(same(requestDto), same(CadastralParcel.class))).willReturn(cadastralParcel);
      // ... CadastralParcelRepository will successfully save CadastralParcel
      given(cadastralParcelRepository.save(same(cadastralParcel))).willReturn(cadastralParcel);
      // ... ConversionService successfully converts from CadastralParcel to FullCadastralParcelResponseDto
      val expectedResponseDto = FullCadastralParcelResponseDtoMother.complete().build();
      given(conversionService.convert(same(cadastralParcel), same(FullCadastralParcelResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... createCadastralParcel is called
      val responseDto = cadastralParcelService.createCadastralParcel(requestDto);

      // THEN
      // ... CadastralParcelResponseDto
      then(cadastralParcelRepository).should(times(1)).save(same(cadastralParcel));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateCadastralParcelById is called")
  public class WHEN_updateCadastralParcelById {

    @Test
    @DisplayName("GIVEN invalid id and UpdateCadastralParcelRequestDto " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidIdAndUpdateCadastralParcelRequestDto_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid id
      val id = 1L;
      given(cadastralParcelRepository.findById(same(id))).willReturn(Optional.empty());
      // ... UpdateCadastralParcelRequestDto
      val requestDto = UpdateCadastralParcelRequestDtoMother.complete().build();

      // WHEN
      // ... updateCadastralParcelById is called
      when(() -> cadastralParcelService.updateCadastralParcelById(id, requestDto));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(EntityNotFoundException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id, UpdateCadastralParcelRequestDto, and foreign logged-in User " +
      "... THEN ForeignUserDataAccessException is thrown")
    public void GIVEN_idAndUpdateCadastralParcelRequestDtoAndForeignUser_THEN_ForeignUserDataAccessException() {
      // GIVEN
      // ... id
      val id = 1L;
      val cadastralParcel = CadastralParcelMother.complete().build();
      given(cadastralParcelRepository.findById(same(id))).willReturn(Optional.of(cadastralParcel));
      // ... UpdateCadastralParcelRequestDto
      val requestDto = UpdateCadastralParcelRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is not equal to User
      val loggedInUser = UserMother.complete().id(cadastralParcel.getUser().getId() + 1).build();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... updateCadastralParcelById is called
      when(() -> cadastralParcelService.updateCadastralParcelById(id, requestDto));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id and UpdateCadastralParcelRequestDto " +
      "... THEN FullCadastralParcelResponseDto is returned")
    public void GIVEN_idAndUpdateCadastralParcelRequestDto_THEN_FullCadastralParcelResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      val cadastralParcel = CadastralParcelMother.complete().build();
      given(cadastralParcelRepository.findById(same(id))).willReturn(Optional.of(cadastralParcel));
      // ... UpdateCadastralParcelRequestDto
      val requestDto = UpdateCadastralParcelRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = cadastralParcel.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... CadastralParcelWithUpdateCadastralParcelRequestDtoValidator successfully validates CadastralParcel with UpdateCadastralParcelRequestDto
      willDoNothing().given(cadastralParcelWithUpdateCadastralParcelRequestDtoValidator).validate(same(cadastralParcel), same(requestDto));
      // ... CadastralParcelMapper successfully partially updates CadastralParcel with UpdateCadastralParcelRequestDto
      given(cadastralParcelMapper.partialUpdate(same(cadastralParcel), same(requestDto))).willReturn(cadastralParcel);
      // ... CadastralParcelRepository successfully saves CadastralParcel
      given(cadastralParcelRepository.save(same(cadastralParcel))).willReturn(cadastralParcel);
      // ... ConversionService successfully converts from CadastralParcel to FullCadastralParcelResponseDto
      val expectedResponseDto = FullCadastralParcelResponseDtoMother.complete().build();
      given(conversionService.convert(same(cadastralParcel), same(FullCadastralParcelResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateCadastralParcelById is called
      val responseDto = cadastralParcelService.updateCadastralParcelById(id, requestDto);

      // THEN
      // ... CadastralParcelResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteCadastralParcelById is called")
  public class WHEN_deleteCadastralParcelById {

    @Test
    @DisplayName("GIVEN invalid id " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidId_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid id
      val id = 1L;
      given(cadastralParcelRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getCadastralParcelById is called
      when(() -> cadastralParcelService.deleteCadastralParcelById(id));

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
      val cadastralParcel = CadastralParcelMother.complete().build();
      given(cadastralParcelRepository.findById(same(id))).willReturn(Optional.of(cadastralParcel));
      // ... CurrentUserService's logged-in User is not equal to CadastralParcel User
      val loggedInUser = UserMother.complete().id(cadastralParcel.getUser().getId() + 1).build();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getCadastralParcelById is called
      when(() -> cadastralParcelService.deleteCadastralParcelById(id));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN void")
    public void GIVEN_id_THEN_void() {
      // GIVEN
      // ... id
      val id = 1L;
      val cadastralParcel = CadastralParcelMother.complete().build();
      given(cadastralParcelRepository.findById(same(id))).willReturn(Optional.of(cadastralParcel));
      // ... CurrentUserService's logged-in User is not equal to CadastralParcel User
      val loggedInUser = cadastralParcel.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... CadastralParcelRepository successfully deletes CadastralParcel
      willDoNothing().given(cadastralParcelRepository).delete(cadastralParcel);

      // WHEN
      cadastralParcelService.deleteCadastralParcelById(id);

      // THEN
      // ... void
    }

  }

}