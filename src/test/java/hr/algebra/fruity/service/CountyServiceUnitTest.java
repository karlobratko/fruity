package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.CountyResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.County;
import hr.algebra.fruity.repository.CountyRepository;
import hr.algebra.fruity.service.impl.CountyServiceImpl;
import hr.algebra.fruity.utils.mother.dto.CountyResponseDtoMother;
import hr.algebra.fruity.utils.mother.model.CountyMother;
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
@DisplayName("County Service Unit Test")
@SuppressWarnings("static-access")
public class CountyServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CountyServiceImpl countyService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private CountyRepository countyRepository;

  @Nested
  @DisplayName("... WHEN getAllCounties is called")
  public class WHEN_getAllCounties {

    @Test
    @DisplayName("GIVEN non-empty CountyRepository " +
      "... THEN List<CountyResponseDto> is returned")
    public void GIVEN_nonEmptyRepository_THEN_ListOfCountyResponseDto() {
      // GIVEN
      // ... non-empty CountyRepository
      val counties = List.of(
        CountyMother.complete().id(1).build(),
        CountyMother.complete().id(2).build(),
        CountyMother.complete().id(3).build()
      );
      given(countyRepository.findAll()).willReturn(counties);
      // ... ConversionService successfully converts from County to CountyResponseDto
      given(conversionService.convert(any(County.class), same(CountyResponseDto.class))).willAnswer(invocation -> CountyResponseDtoMother.complete().id(invocation.<County>getArgument(0).getId()).build());

      // WHEN
      // ... getAllCounties is called
      val responseDtos = countyService.getAllCounties();

      // THEN
      // ... List<CountyResponseDto> is returned
      and.then(responseDtos).satisfies(it -> {
        and.then(responseDtos).hasSize(counties.size());
        and.then(responseDtos).allSatisfy(child -> and.then(child).isNotNull());
      });
    }

  }

  @Nested
  @DisplayName("... WHEN getCountyById is called")
  public class WHEN_getCountyById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN CountyResponseDto is returned")
    public void GIVEN_id_THEN_CountyResponseDto() {
      // GIVEN
      // ... id
      val id = 1;
      val county = CountyMother.complete().build();
      given(countyRepository.findById(same(id))).willReturn(Optional.of(county));
      // ... ConversionService successfully converts from County to CountyResponseDto
      val expectedResponseDto = CountyResponseDtoMother.complete().build();
      given(conversionService.convert(same(county), same(CountyResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... getCountyById is called
      val responseDto = countyService.getCountyById(id);

      // THEN
      // ... CountyResponseDto is returned
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
      given(countyRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> countyService.getById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(EntityNotFoundException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN County is returned")
    public void GIVEN_id_THEN_County() {
      // GIVEN
      // ... id
      val id = 1;
      val expectedCounty = CountyMother.complete().build();
      given(countyRepository.findById(same(id))).willReturn(Optional.of(expectedCounty));

      // WHEN
      // ... getById is called
      val returnedCounty = countyService.getById(id);

      // THEN
      // ... CountyResponseDto is returned
      and.then(returnedCounty)
        .isNotNull()
        .isEqualTo(expectedCounty);
    }

  }

}
