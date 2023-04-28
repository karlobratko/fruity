package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.ArcodeParcelToArcodeParcelResponseDtoConverter;
import hr.algebra.fruity.converter.CadastralParcelToCadastralParcelResponseDtoConverter;
import hr.algebra.fruity.converter.CadastralParcelToFullCadastralParcelResponseDtoConverter;
import hr.algebra.fruity.converter.CreateCadastralParcelRequestDtoToJoinedCreateCadastralParcelRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateCadastralParcelRequestDtoToCadastralParcelConverter;
import hr.algebra.fruity.converter.UpdateCadastralParcelRequestDtoToJoinedUpdateCadastralParcelRequestDtoConverter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.CadastralParcelMapper;
import hr.algebra.fruity.repository.ArcodeParcelRepository;
import hr.algebra.fruity.repository.CadastralParcelRepository;
import hr.algebra.fruity.service.impl.CurrentUserCadastralParcelService;
import hr.algebra.fruity.utils.mother.dto.CreateCadastralParcelRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullCadastralParcelResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedCreateCadastralParcelRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedUpdateCadastralParcelRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateCadastralParcelRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.CadastralParcelMother;
import hr.algebra.fruity.validator.CadastralParcelWithJoinedUpdateCadastralParcelRequestDtoValidator;
import hr.algebra.fruity.validator.JoinedCreateCadastralParcelRequestDtoValidator;
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
@DisplayName("CadastralParcel Unit Test")
@SuppressWarnings("static-access")
public class CadastralParcelServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserCadastralParcelService cadastralParcelService;

  @Mock
  private CadastralParcelToCadastralParcelResponseDtoConverter toCadastralParcelResponseDtoConverter;

  @Mock
  private CadastralParcelToFullCadastralParcelResponseDtoConverter toFullCadastralParcelResponseDtoConverter;

  @Mock
  private CreateCadastralParcelRequestDtoToJoinedCreateCadastralParcelRequestDtoConverter toJoinedCreateCadastralParcelRequestDtoConverter;

  @Mock
  private JoinedCreateCadastralParcelRequestDtoToCadastralParcelConverter fromJoinedCreateCadastralParcelRequestDtoConverter;

  @Mock
  private ArcodeParcelToArcodeParcelResponseDtoConverter toArcodeParcelResponseDtoConverter;

  @Mock
  private JoinedCreateCadastralParcelRequestDtoValidator joinedCreateCadastralParcelRequestDtoValidator;

  @Mock
  private UpdateCadastralParcelRequestDtoToJoinedUpdateCadastralParcelRequestDtoConverter toJoinedUpdateCadastralParcelRequestDtoConverter;

  @Mock
  private CadastralParcelWithJoinedUpdateCadastralParcelRequestDtoValidator cadastralParcelWithJoinedUpdateCadastralParcelRequestDtoValidator;

  @Mock
  private CadastralParcelMapper cadastralParcelMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private CadastralParcelRepository cadastralParcelRepository;

  @Mock
  private ArcodeParcelRepository arcodeParcelRepository;

  @Nested
  @DisplayName("WHEN getCadastralParcelById is called")
  public class WHEN_getCadastralParcelById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN FullCadastralParcelResponseDto is returned")
    public void GIVEN_id_THEN_FullCadastralParcelResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... CadastralParcelRepository fails to findByIdAndUserId
      val cadastralParcel = CadastralParcelMother.complete().build();
      given(cadastralParcelRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(cadastralParcel));
      // ... CadastralParcelToFullCadastralParcelResponseDto successfully converts
      val expectedResponseDto = FullCadastralParcelResponseDtoMother.complete().build();
      given(toFullCadastralParcelResponseDtoConverter.convert(same(cadastralParcel))).willReturn(expectedResponseDto);

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
      // ... CreateCadastralParcelRequestDtoToJoinedCreateCadastralParcelRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedCreateCadastralParcelRequestDtoMother.complete().build();
      given(toJoinedCreateCadastralParcelRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... JoinedCreateCadastralParcelRequestDtoValidator successfully validates
      willDoNothing().given(joinedCreateCadastralParcelRequestDtoValidator).validate(same(joinedRequestDto));
      // ... JoinedCreateCadastralParcelRequestDtoToCadastralParcelConverter successfully converts
      val cadastralParcel = CadastralParcelMother.complete().build();
      given(fromJoinedCreateCadastralParcelRequestDtoConverter.convert(same(joinedRequestDto))).willReturn(cadastralParcel);
      // ... CadastralParcelRepository will successfully save
      given(cadastralParcelRepository.save(same(cadastralParcel))).willReturn(cadastralParcel);
      // ... CadastralParcelToFullCadastralParcelResponseDtoConverter successfully converts
      val expectedResponseDto = FullCadastralParcelResponseDtoMother.complete().build();
      given(toFullCadastralParcelResponseDtoConverter.convert(same(cadastralParcel))).willReturn(expectedResponseDto);

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
    @DisplayName("GIVEN id and UpdateCadastralParcelRequestDto " +
      "... THEN FullCadastralParcelResponseDto is returned")
    public void GIVEN_idAndUpdateCadastralParcelRequestDto_THEN_FullCadastralParcelResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... UpdateCadastralParcelRequestDto
      val requestDto = UpdateCadastralParcelRequestDtoMother.complete().build();
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... CadastralParcelRepository fails to findByIdAndUserId
      val cadastralParcel = CadastralParcelMother.complete().build();
      given(cadastralParcelRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(cadastralParcel));
      // ... UpdateCadastralParcelRequestDtoToJoinedUpdateCadastralParcelRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedUpdateCadastralParcelRequestDtoMother.complete().build();
      given(toJoinedUpdateCadastralParcelRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... CadastralParcelWithUpdateCadastralParcelRequestDtoValidator successfully validates
      willDoNothing().given(cadastralParcelWithJoinedUpdateCadastralParcelRequestDtoValidator).validate(same(cadastralParcel), same(joinedRequestDto));
      // ... CadastralParcelMapper successfully partially updates
      given(cadastralParcelMapper.partialUpdate(same(cadastralParcel), same(joinedRequestDto))).willReturn(cadastralParcel);
      // ... CadastralParcelRepository successfully saves
      given(cadastralParcelRepository.save(same(cadastralParcel))).willReturn(cadastralParcel);
      // ... CadastralParcelToFullCadastralParcelResponseDtoConverter successfully converts
      val expectedResponseDto = FullCadastralParcelResponseDtoMother.complete().build();
      given(toFullCadastralParcelResponseDtoConverter.convert(same(cadastralParcel))).willReturn(expectedResponseDto);

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
    @DisplayName("GIVEN id " +
      "... THEN void")
    public void GIVEN_id_THEN_void() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... CadastralParcelRepository fails to findByIdAndUserId
      val cadastralParcel = CadastralParcelMother.complete().build();
      given(cadastralParcelRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(cadastralParcel));
      // ... CadastralParcelRepository successfully deletes CadastralParcel
      willDoNothing().given(cadastralParcelRepository).delete(cadastralParcel);

      // WHEN
      // ... deleteCadastralParcelById is called
      cadastralParcelService.deleteCadastralParcelById(id);

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
      // ... CadastralParcelRepository fails to findByIdAndUserId
      given(cadastralParcelRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> cadastralParcelService.getById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN CadastralParcel is returned")
    public void GIVEN_id_THEN_CadastralParcel() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... CadastralParcelRepository fails to findByIdAndUserId
      val expectedCadastralParcel = CadastralParcelMother.complete().build();
      given(cadastralParcelRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(expectedCadastralParcel));

      // WHEN
      // ... getById is called
      val returnedCadastralParcel = cadastralParcelService.getById(id);

      // THEN
      // ... CadastralParcelResponseDto is returned
      and.then(returnedCadastralParcel)
        .isNotNull()
        .isEqualTo(expectedCadastralParcel);
    }

  }

}