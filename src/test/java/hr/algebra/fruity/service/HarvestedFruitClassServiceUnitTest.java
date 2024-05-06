package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.HarvestedFruitClassToHarvestedFruitClassResponseDtoConverter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.HarvestedFruitClass;
import hr.algebra.fruity.repository.HarvestedFruitClassRepository;
import hr.algebra.fruity.service.impl.HarvestedFruitClassServiceImpl;
import hr.algebra.fruity.utils.mother.dto.HarvestedFruitClassResponseDtoMother;
import hr.algebra.fruity.utils.mother.model.HarvestedFruitClassMother;
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
@DisplayName("HarvestedFruitClassService Unit Test")
@SuppressWarnings("static-access")
public class HarvestedFruitClassServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private HarvestedFruitClassServiceImpl harvestedFruitClassService;

  @Mock
  private HarvestedFruitClassToHarvestedFruitClassResponseDtoConverter toHarvestedFruitClassResponseDtoConverter;

  @Mock
  private HarvestedFruitClassRepository harvestedFruitClassRepository;

  @Nested
  @DisplayName("... WHEN getAllHarvestedFruitClasses is called")
  public class WHEN_getAllHarvestedFruitClasses {

    @Test
    @DisplayName("GIVEN non-empty HarvestedFruitClassRepository " +
      "... THEN List<HarvestedFruitClassResponseDto> is returned")
    public void GIVEN_nonEmptyRepository_THEN_ListOfHarvestedFruitClassResponseDto() {
      // GIVEN
      // ... non-empty HarvestedFruitClassRepository
      val harvestedFruitClasses = List.of(
        HarvestedFruitClassMother.complete().id(1).build(),
        HarvestedFruitClassMother.complete().id(2).build(),
        HarvestedFruitClassMother.complete().id(3).build()
      );
      given(harvestedFruitClassRepository.findAll()).willReturn(harvestedFruitClasses);
      // ... ConversionService successfully converts from HarvestedFruitClass to HarvestedFruitClassResponseDto
      given(toHarvestedFruitClassResponseDtoConverter.convert(any(HarvestedFruitClass.class))).willAnswer(invocation -> HarvestedFruitClassResponseDtoMother.complete().id(invocation.<HarvestedFruitClass>getArgument(0).getId()).build());

      // WHEN
      // ... getAllHarvestedFruitClasses is called
      val responseDtos = harvestedFruitClassService.getAllHarvestedFruitClasses();

      // THEN
      // ... List<HarvestedFruitClassResponseDto> is returned
      and.then(responseDtos).satisfies(it -> {
        and.then(responseDtos).hasSize(harvestedFruitClasses.size());
        and.then(responseDtos).allSatisfy(child -> and.then(child).isNotNull());
      });
    }

  }

  @Nested
  @DisplayName("... WHEN getHarvestedFruitClassById is called")
  public class WHEN_getHarvestedFruitClassById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN HarvestedFruitClassResponseDto is returned")
    public void GIVEN_id_THEN_HarvestedFruitClassResponseDto() {
      // GIVEN
      // ... id
      val id = 1;
      val harvestedFruitClass = HarvestedFruitClassMother.complete().build();
      given(harvestedFruitClassRepository.findById(same(id))).willReturn(Optional.of(harvestedFruitClass));
      // ... ConversionService successfully converts from HarvestedFruitClass to HarvestedFruitClassResponseDto
      val expectedResponseDto = HarvestedFruitClassResponseDtoMother.complete().build();
      given(toHarvestedFruitClassResponseDtoConverter.convert(same(harvestedFruitClass))).willReturn(expectedResponseDto);

      // WHEN
      // ... getHarvestedFruitClassById is called
      val responseDto = harvestedFruitClassService.getHarvestedFruitClassById(id);

      // THEN
      // ... HarvestedFruitClassResponseDto is returned
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
      given(harvestedFruitClassRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> harvestedFruitClassService.getById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN HarvestedFruitClass is returned")
    public void GIVEN_id_THEN_HarvestedFruitClass() {
      // GIVEN
      // ... id
      val id = 1;
      val expectedHarvestedFruitClass = HarvestedFruitClassMother.complete().build();
      given(harvestedFruitClassRepository.findById(same(id))).willReturn(Optional.of(expectedHarvestedFruitClass));

      // WHEN
      // ... getById is called
      val returnedHarvestedFruitClass = harvestedFruitClassService.getById(id);

      // THEN
      // ... HarvestedFruitClassResponseDto is returned
      and.then(returnedHarvestedFruitClass)
        .isNotNull()
        .isEqualTo(expectedHarvestedFruitClass);
    }

  }

}
