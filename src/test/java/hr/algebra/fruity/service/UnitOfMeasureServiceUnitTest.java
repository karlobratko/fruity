package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.UnitOfMeasureToFullUnitOfMeasureResponseDtoConverter;
import hr.algebra.fruity.converter.UnitOfMeasureToUnitOfMeasureResponseDtoConverter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.UnitOfMeasure;
import hr.algebra.fruity.repository.UnitOfMeasureRepository;
import hr.algebra.fruity.service.impl.UnitOfMeasureServiceImpl;
import hr.algebra.fruity.utils.mother.dto.FullUnitOfMeasureResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.UnitOfMeasureResponseDtoMother;
import hr.algebra.fruity.utils.mother.model.UnitOfMeasureMother;
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

import static com.googlecode.catchexception.apis.BDDCatchException.caughtException;
import static com.googlecode.catchexception.apis.BDDCatchException.when;
import static org.assertj.core.api.BDDAssertions.and;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("UnitOfMeasure Service Unit Test")
@SuppressWarnings("static-access")
public class UnitOfMeasureServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private UnitOfMeasureServiceImpl unitOfMeasureService;

  @Mock
  private UnitOfMeasureToUnitOfMeasureResponseDtoConverter toUnitOfMeasureResponseDtoConverter;

  @Mock
  private UnitOfMeasureToFullUnitOfMeasureResponseDtoConverter toFullUnitOfMeasureResponseDtoConverter;

  @Mock
  private UnitOfMeasureRepository unitOfMeasureRepository;

  @Nested
  @DisplayName("... WHEN getAllUnitOfMeasures is called")
  public class WHEN_getAllUnitOfMeasures {

    @Test
    @DisplayName("GIVEN non-empty UnitOfMeasureRepository " +
      "... THEN List<UnitOfMeasureResponseDto> is returned")
    public void GIVEN_nonEmptyRepository_THEN_ListOfUnitOfMeasureResponseDto() {
      // GIVEN
      // ... non-empty UnitOfMeasureRepository
      val unitsOfMeasure = List.of(
        UnitOfMeasureMother.complete().id(1).build(),
        UnitOfMeasureMother.complete().id(2).build(),
        UnitOfMeasureMother.complete().id(3).build()
      );
      given(unitOfMeasureRepository.findAll()).willReturn(unitsOfMeasure);
      // ... UnitOfMeasureToUnitOfMeasureResponseDtoConverter successfully converts
      given(toUnitOfMeasureResponseDtoConverter.convert(any(UnitOfMeasure.class))).willAnswer(invocation -> UnitOfMeasureResponseDtoMother.complete().id(invocation.<UnitOfMeasure>getArgument(0).getId()).build());

      // WHEN
      // ... getAllUnitOfMeasures is called
      val responseDtos = unitOfMeasureService.getAllUnitsOfMeasure();

      // THEN
      // ... List<UnitOfMeasureResponseDto> is returned
      and.then(responseDtos).satisfies(it -> {
        and.then(responseDtos).hasSize(unitsOfMeasure.size());
        and.then(responseDtos).allSatisfy(child -> and.then(child).isNotNull());
      });
    }

  }

  @Nested
  @DisplayName("... WHEN getUnitOfMeasureById is called")
  public class WHEN_getUnitOfMeasureById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN UnitOfMeasureResponseDto is returned")
    public void GIVEN_id_THEN_UnitOfMeasureResponseDto() {
      // GIVEN
      // ... id
      val id = 1;
      val unitOfMeasure = UnitOfMeasureMother.complete().build();
      given(unitOfMeasureRepository.findById(same(id))).willReturn(Optional.of(unitOfMeasure));
      // ... UnitOfMeasureToFullUnitOfMeasureResponseDtoConverter successfully converts
      val expectedResponseDto = FullUnitOfMeasureResponseDtoMother.complete().build();
      given(toFullUnitOfMeasureResponseDtoConverter.convert(same(unitOfMeasure))).willReturn(expectedResponseDto);

      // WHEN
      // ... getUnitOfMeasureById is called
      val responseDto = unitOfMeasureService.getUnitOfMeasureById(id);

      // THEN
      // ... UnitOfMeasureResponseDto is returned
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
      given(unitOfMeasureRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> unitOfMeasureService.getById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN UnitOfMeasure is returned")
    public void GIVEN_id_THEN_UnitOfMeasure() {
      // GIVEN
      // ... id
      val id = 1;
      val expectedUnitOfMeasure = UnitOfMeasureMother.complete().build();
      given(unitOfMeasureRepository.findById(same(id))).willReturn(Optional.of(expectedUnitOfMeasure));

      // WHEN
      // ... getById is called
      val returnedUnitOfMeasure = unitOfMeasureService.getById(id);

      // THEN
      // ... UnitOfMeasureResponseDto is returned
      and.then(returnedUnitOfMeasure)
        .isNotNull()
        .isEqualTo(expectedUnitOfMeasure);
    }

  }

}
