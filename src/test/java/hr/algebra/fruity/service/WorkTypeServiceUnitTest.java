package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.FullWorkTypeResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.WorkType;
import hr.algebra.fruity.repository.WorkTypeRepository;
import hr.algebra.fruity.service.impl.WorkTypeServiceImpl;
import hr.algebra.fruity.utils.mother.dto.FullWorkTypeResponseDtoMother;
import hr.algebra.fruity.utils.mother.model.WorkTypeMother;
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
@DisplayName("WorkType Service Unit Test")
@SuppressWarnings("static-access")
public class WorkTypeServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private WorkTypeServiceImpl workTypeService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private WorkTypeRepository workTypeRepository;

  @Nested
  @DisplayName("... WHEN getAllWorkTypes is called")
  public class WHEN_getAllWorkTypes {

    @Test
    @DisplayName("GIVEN non-empty WorkTypeRepository " +
      "... THEN List<FullWorkTypeResponseDto> is returned")
    public void GIVEN_nonEmptyRepository_THEN_ListOfFullWorkTypeResponseDto() {
      // GIVEN
      // ... non-empty WorkTypeRepository
      val counties = List.of(
        WorkTypeMother.complete().id(1).build(),
        WorkTypeMother.complete().id(2).build(),
        WorkTypeMother.complete().id(3).build()
      );
      given(workTypeRepository.findAll()).willReturn(counties);
      // ... ConversionService successfully converts from WorkType to FullWorkTypeResponseDto
      given(conversionService.convert(any(WorkType.class), same(FullWorkTypeResponseDto.class))).willAnswer(invocation -> FullWorkTypeResponseDtoMother.complete().id(invocation.<WorkType>getArgument(0).getId()).build());

      // WHEN
      // ... getAllWorkTypes is called
      val responseDtos = workTypeService.getAllWorkTypes();

      // THEN
      // ... List<WorkTypeResponseDto> is returned
      and.then(responseDtos).satisfies(it -> {
        and.then(responseDtos).hasSize(counties.size());
        and.then(responseDtos).allSatisfy(child -> and.then(child).isNotNull());
      });
    }

  }

  @Nested
  @DisplayName("... WHEN getWorkTypeById is called")
  public class WHEN_getWorkTypeById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN WorkTypeResponseDto is returned")
    public void GIVEN_id_THEN_WorkTypeResponseDto() {
      // GIVEN
      // ... id
      val id = 1;
      val workType = WorkTypeMother.complete().build();
      given(workTypeRepository.findById(same(id))).willReturn(Optional.of(workType));
      // ... ConversionService successfully converts from WorkType to WorkTypeResponseDto
      val expectedResponseDto = FullWorkTypeResponseDtoMother.complete().build();
      given(conversionService.convert(same(workType), same(FullWorkTypeResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... getWorkTypeById is called
      val responseDto = workTypeService.getWorkTypeById(id);

      // THEN
      // ... WorkTypeResponseDto is returned
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
      given(workTypeRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> workTypeService.getById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(EntityNotFoundException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN WorkType is returned")
    public void GIVEN_id_THEN_WorkType() {
      // GIVEN
      // ... id
      val id = 1;
      val expectedWorkType = WorkTypeMother.complete().build();
      given(workTypeRepository.findById(same(id))).willReturn(Optional.of(expectedWorkType));

      // WHEN
      // ... getById is called
      val returnedWorkType = workTypeService.getById(id);

      // THEN
      // ... WorkTypeResponseDto is returned
      and.then(returnedWorkType)
        .isNotNull()
        .isEqualTo(expectedWorkType);
    }

  }

}
