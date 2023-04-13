package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.CadastralParcelOwnershipStatusResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.CadastralParcelOwnershipStatus;
import hr.algebra.fruity.repository.CadastralParcelOwnershipStatusRepository;
import hr.algebra.fruity.service.impl.CadastralParcelOwnershipStatusServiceImpl;
import hr.algebra.fruity.utils.mother.dto.CadastralParcelOwnershipStatusResponseDtoMother;
import hr.algebra.fruity.utils.mother.model.CadastralParcelOwnershipStatusMother;
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
@DisplayName("CadastralParcelOwnershipStatus Service Unit Test")
@SuppressWarnings("static-access")
public class CadastralParcelOwnershipStatusServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CadastralParcelOwnershipStatusServiceImpl cadastralParcelOwnershipStatusService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private CadastralParcelOwnershipStatusRepository cadastralParcelOwnershipStatusRepository;

  @Nested
  @DisplayName("... WHEN getAllCadastralParcelOwnershipStatuses is called")
  public class WHEN_getAllCadastralParcelOwnershipStatuses {

    @Test
    @DisplayName("GIVEN non-empty CadastralParcelOwnershipStatusRepository " +
      "... THEN List<CadastralParcelOwnershipStatusResponseDto> is returned")
    public void GIVEN_nonEmptyRepository_THEN_ListOfCadastralParcelOwnershipStatusResponseDto() {
      // GIVEN
      // ... non-empty CadastralParcelOwnershipStatusRepository
      val cadastralParcelOwnershipStatuses = List.of(
        CadastralParcelOwnershipStatusMother.complete().id(1).build(),
        CadastralParcelOwnershipStatusMother.complete().id(2).build(),
        CadastralParcelOwnershipStatusMother.complete().id(3).build()
      );
      given(cadastralParcelOwnershipStatusRepository.findAll()).willReturn(cadastralParcelOwnershipStatuses);
      // ... ConversionService successfully converts from CadastralParcelOwnershipStatus to CadastralParcelOwnershipStatusResponseDto
      given(conversionService.convert(any(CadastralParcelOwnershipStatus.class), same(CadastralParcelOwnershipStatusResponseDto.class))).willAnswer(invocation -> CadastralParcelOwnershipStatusResponseDtoMother.complete().id(invocation.<CadastralParcelOwnershipStatus>getArgument(0).getId()).build());

      // WHEN
      // ... getAllCadastralParcelOwnershipStatuses is called
      val responseDtos = cadastralParcelOwnershipStatusService.getAllCadastralParcelOwnershipStatuses();

      // THEN
      // ... List<CadastralParcelOwnershipStatusResponseDto> is returned
      and.then(responseDtos).satisfies(it -> {
        and.then(responseDtos).hasSize(cadastralParcelOwnershipStatuses.size());
        and.then(responseDtos).allSatisfy(child -> and.then(child).isNotNull());
      });
    }

  }

  @Nested
  @DisplayName("... WHEN getCadastralParcelOwnershipStatusById is called")
  public class WHEN_getCadastralParcelOwnershipStatusById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN CadastralParcelOwnershipStatusResponseDto is returned")
    public void GIVEN_id_THEN_CadastralParcelOwnershipStatusResponseDto() {
      // GIVEN
      // ... id
      val id = 1;
      val cadastralMunicipality = CadastralParcelOwnershipStatusMother.complete().build();
      given(cadastralParcelOwnershipStatusRepository.findById(same(id))).willReturn(Optional.of(cadastralMunicipality));
      // ... ConversionService successfully converts from CadastralParcelOwnershipStatus to CadastralParcelOwnershipStatusResponseDto
      val expectedResponseDto = CadastralParcelOwnershipStatusResponseDtoMother.complete().build();
      given(conversionService.convert(same(cadastralMunicipality), same(CadastralParcelOwnershipStatusResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... getCadastralParcelOwnershipStatusById is called
      val responseDto = cadastralParcelOwnershipStatusService.getCadastralParcelOwnershipStatusById(id);

      // THEN
      // ... CadastralParcelOwnershipStatusResponseDto is returned
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
      given(cadastralParcelOwnershipStatusRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> cadastralParcelOwnershipStatusService.getById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(EntityNotFoundException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN CadastralParcelOwnershipStatus is returned")
    public void GIVEN_id_THEN_CadastralParcelOwnershipStatus() {
      // GIVEN
      // ... id
      val id = 1;
      val expectedCadastralParcelOwnershipStatus = CadastralParcelOwnershipStatusMother.complete().build();
      given(cadastralParcelOwnershipStatusRepository.findById(same(id))).willReturn(Optional.of(expectedCadastralParcelOwnershipStatus));

      // WHEN
      // ... getById is called
      val returnedCadastralParcelOwnershipStatus = cadastralParcelOwnershipStatusService.getById(id);

      // THEN
      // ... CadastralParcelOwnershipStatusResponseDto is returned
      and.then(returnedCadastralParcelOwnershipStatus)
        .isNotNull()
        .isEqualTo(expectedCadastralParcelOwnershipStatus);
    }

  }

}