package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.CadastralMunicipalityToCadastralMunicipalityResponseDtoConverter;
import hr.algebra.fruity.converter.CadastralMunicipalityToFullCadastralMunicipalityResponseDtoConverter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.repository.CadastralMunicipalityRepository;
import hr.algebra.fruity.service.impl.CadastralMunicipalityServiceImpl;
import hr.algebra.fruity.utils.mother.dto.FullCadastralMunicipalityResponseDtoMother;
import hr.algebra.fruity.utils.mother.model.CadastralMunicipalityMother;
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

@ExtendWith(MockitoExtension.class)
@DisplayName("CadastralMunicipality Service Unit Test")
@SuppressWarnings("static-access")
public class CadastralMunicipalityServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CadastralMunicipalityServiceImpl cadastralMunicipalityService;

  @Mock
  private CadastralMunicipalityToCadastralMunicipalityResponseDtoConverter toCadastralMunicipalityResponseDtoConverter;

  @Mock
  private CadastralMunicipalityToFullCadastralMunicipalityResponseDtoConverter toFullCadastralMunicipalityResponseDtoConverter;

  @Mock
  private CadastralMunicipalityRepository cadastralMunicipalityRepository;

  @Nested
  @DisplayName("... WHEN getAllCadastralMunicipalities is called")
  public class WHEN_getAllCadastralMunicipalities {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN CadastralMunicipalityResponseDto is returned")
    public void GIVEN_id_THEN_CadastralMunicipalityResponseDto() {
      // GIVEN
      // ... id
      val id = 1;
      val cadastralMunicipality = CadastralMunicipalityMother.complete().build();
      given(cadastralMunicipalityRepository.findById(same(id))).willReturn(Optional.of(cadastralMunicipality));
      // ... CadastralMunicipalityToFullCadastralMunicipalityResponseDtoConverter successfully converts
      val expectedResponseDto = FullCadastralMunicipalityResponseDtoMother.complete().build();
      given(toFullCadastralMunicipalityResponseDtoConverter.convert(same(cadastralMunicipality))).willReturn(expectedResponseDto);

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

  @Nested
  @DisplayName("WHEN getById is called")
  public class WHEN_getById {

    @Test
    @DisplayName("GIVEN invalid id " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidId_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid id
      val id = 1;
      given(cadastralMunicipalityRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> cadastralMunicipalityService.getById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN CadastralMunicipality is returned")
    public void GIVEN_id_THEN_CadastralMunicipality() {
      // GIVEN
      // ... id
      val id = 1;
      val expectedCadastralMunicipality = CadastralMunicipalityMother.complete().build();
      given(cadastralMunicipalityRepository.findById(same(id))).willReturn(Optional.of(expectedCadastralMunicipality));

      // WHEN
      // ... getById is called
      val returnedCadastralMunicipality = cadastralMunicipalityService.getById(id);

      // THEN
      // ... CadastralMunicipalityResponseDto is returned
      and.then(returnedCadastralMunicipality)
        .isNotNull()
        .isEqualTo(expectedCadastralMunicipality);
    }

  }

}
