package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.FruitCultivarResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.FruitCultivar;
import hr.algebra.fruity.repository.FruitCultivarRepository;
import hr.algebra.fruity.service.impl.FruitCultivarServiceImpl;
import hr.algebra.fruity.utils.mother.dto.FruitCultivarResponseDtoMother;
import hr.algebra.fruity.utils.mother.model.FruitCultivarMother;
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
@DisplayName("FruitCultivar Service Unit Test")
@SuppressWarnings("static-access")
public class FruitCultivarServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private FruitCultivarServiceImpl fruitCultivarService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private FruitCultivarRepository fruitCultivarRepository;

  @Nested
  @DisplayName("... WHEN getAllFruitCultivars is called")
  public class WHEN_getAllFruitCultivars {

    @Test
    @DisplayName("GIVEN non-empty FruitCultivarRepository " +
      "... THEN List<FruitCultivarResponseDto> is returned")
    public void GIVEN_nonEmptyRepository_THEN_ListOfFruitCultivarResponseDto() {
      // GIVEN
      // ... non-empty FruitCultivarRepository
      val fruitCultivars = List.of(
        FruitCultivarMother.complete().id(1).build(),
        FruitCultivarMother.complete().id(2).build(),
        FruitCultivarMother.complete().id(3).build()
      );
      given(fruitCultivarRepository.findAll()).willReturn(fruitCultivars);
      // ... ConversionService successfully converts from FruitCultivar to FruitCultivarResponseDto
      given(conversionService.convert(any(FruitCultivar.class), same(FruitCultivarResponseDto.class))).willAnswer(invocation -> FruitCultivarResponseDtoMother.complete().id(invocation.<FruitCultivar>getArgument(0).getId()).build());

      // WHEN
      // ... getAllFruitCultivars is called
      val responseDtos = fruitCultivarService.getAllFruitCultivars();

      // THEN
      // ... List<FruitCultivarResponseDto> is returned
      and.then(responseDtos).satisfies(it -> {
        and.then(responseDtos).hasSize(fruitCultivars.size());
        and.then(responseDtos).allSatisfy(child -> and.then(child).isNotNull());
      });
    }

  }

  @Nested
  @DisplayName("... WHEN getFruitCultivarById is called")
  public class WHEN_getFruitCultivarById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN FruitCultivarResponseDto is returned")
    public void GIVEN_id_THEN_FruitCultivarResponseDto() {
      // GIVEN
      // ... id
      val id = 1;
      val fruitCultivar = FruitCultivarMother.complete().build();
      given(fruitCultivarRepository.findById(same(id))).willReturn(Optional.of(fruitCultivar));
      // ... ConversionService successfully converts from FruitCultivar to FruitCultivarResponseDto
      val expectedResponseDto = FruitCultivarResponseDtoMother.complete().build();
      given(conversionService.convert(same(fruitCultivar), same(FruitCultivarResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... getFruitCultivarById is called
      val responseDto = fruitCultivarService.getFruitCultivarById(id);

      // THEN
      // ... FruitCultivarResponseDto is returned
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
      given(fruitCultivarRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> fruitCultivarService.getById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(EntityNotFoundException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN FruitCultivar is returned")
    public void GIVEN_id_THEN_FruitCultivar() {
      // GIVEN
      // ... id
      val id = 1;
      val expectedFruitCultivar = FruitCultivarMother.complete().build();
      given(fruitCultivarRepository.findById(same(id))).willReturn(Optional.of(expectedFruitCultivar));

      // WHEN
      // ... getById is called
      val returnedFruitCultivar = fruitCultivarService.getById(id);

      // THEN
      // ... FruitCultivarResponseDto is returned
      and.then(returnedFruitCultivar)
        .isNotNull()
        .isEqualTo(expectedFruitCultivar);
    }

  }

}
