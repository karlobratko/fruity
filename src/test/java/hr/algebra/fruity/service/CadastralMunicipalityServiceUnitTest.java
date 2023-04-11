package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.CadastralMunicipalityResponseDto;
import hr.algebra.fruity.dto.response.FullCadastralMunicipalityResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.CadastralMunicipality;
import hr.algebra.fruity.repository.CadastralMunicipalityRepository;
import hr.algebra.fruity.service.impl.CadastralMunicipalityServiceImpl;
import hr.algebra.fruity.utils.mother.dto.CadastralMunicipalityResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullCadastralMunicipalityResponseDtoMother;
import hr.algebra.fruity.utils.mother.model.CadastralMunicipalityMother;
import java.util.List;
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

@ExtendWith(MockitoExtension.class)
@DisplayName("CadastralMunicipality Service Unit Test")
@SuppressWarnings("static-access")
public class CadastralMunicipalityServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CadastralMunicipalityServiceImpl cadastralMunicipalityService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private CadastralMunicipalityRepository cadastralMunicipalityRepository;

  @Nested
  @DisplayName("... WHEN getAllCadastralMunicipalities is called")
  public class WHEN_getAllCadastralMunicipalities {

    @Test
    @DisplayName("GIVEN non-empty CadastralMunicipalityRepository " +
      "... THEN List<CadastralMunicipalityResponseDto> is returned")
    public void GIVEN_nonEmptyRepository_THEN_ListOfCadastralMunicipalityResponseDto() {
      // GIVEN
      // ... non-empty CadastralMunicipalityRepository
      val cadastralMunicipalities = List.of(
        CadastralMunicipalityMother.complete().id(1).build(),
        CadastralMunicipalityMother.complete().id(2).build(),
        CadastralMunicipalityMother.complete().id(3).build()
      );
      given(cadastralMunicipalityRepository.findAll()).willReturn(cadastralMunicipalities);
      // ... ConversionService successfully converts from CadastralMunicipality to CadastralMunicipalityResponseDto
      given(conversionService.convert(any(CadastralMunicipality.class), same(CadastralMunicipalityResponseDto.class))).willAnswer(invocation -> CadastralMunicipalityResponseDtoMother.complete().id(invocation.<CadastralMunicipality>getArgument(0).getId()).build());

      // WHEN
      // ... getAllCadastralMunicipalities is called
      val responseDtos = cadastralMunicipalityService.getAllCadastralMunicipalities();

      // THEN
      // ... List<CadastralMunicipalityResponseDto> is returned
      and.then(responseDtos).satisfies(it -> {
        and.then(responseDtos).hasSize(cadastralMunicipalities.size());
        and.then(responseDtos).allSatisfy(child -> and.then(child).isNotNull());
      });
    }

  }

  @Nested
  @DisplayName("... WHEN getCadastralMunicipalityById is called")
  public class WHEN_getCadastralMunicipalityById {

    @Test
    @DisplayName("GIVEN invalid id " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidId_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid id
      val id = 1;
      given(cadastralMunicipalityRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getCadastralMunicipalityById is called
      when(() -> cadastralMunicipalityService.getCadastralMunicipalityById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(EntityNotFoundException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN CadastralMunicipalityResponseDto is returned")
    public void GIVEN_id_THEN_CadastralMunicipalityResponseDto() {
      // GIVEN
      // ... id
      val id = 1;
      val cadastralMunicipality = CadastralMunicipalityMother.complete().build();
      given(cadastralMunicipalityRepository.findById(same(id))).willReturn(Optional.of(cadastralMunicipality));
      // ... ConversionService successfully converts from CadastralMunicipality to CadastralMunicipalityResponseDto
      val expectedResponseDto = FullCadastralMunicipalityResponseDtoMother.complete().build();
      given(conversionService.convert(same(cadastralMunicipality), same(FullCadastralMunicipalityResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... getCadastralMunicipalityById is called
      val responseDto = cadastralMunicipalityService.getCadastralMunicipalityById(id);

      // THEN
      // ... CadastralMunicipalityResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

}
