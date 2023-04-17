package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.FullRowResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.RowMapper;
import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.repository.RowRepository;
import hr.algebra.fruity.service.impl.CurrentUserRowService;
import hr.algebra.fruity.utils.mother.dto.CreateRowRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullRowResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateRowRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.RowMother;
import hr.algebra.fruity.utils.mother.model.UserMother;
import java.util.Collections;
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
@DisplayName("Row Unit Test")
@SuppressWarnings("static-access")
public class RowServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserRowService rowService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private RowMapper rowMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private RowRepository rowRepository;

  @Mock
  private RowClusterService rowClusterService;

  @Nested
  @DisplayName("WHEN getRowById is called")

  public class WHEN_getRowById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN FullRowResponseDto is returned")
    public void GIVEN_id_THEN_FullRowResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      val row = RowMother.complete().build();
      given(rowRepository.findById(same(id))).willReturn(Optional.of(row));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = row.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... ConversionService successfully converts from User to FullRowResponseDto
      val expectedResponseDto = FullRowResponseDtoMother.complete().build();
      given(conversionService.convert(same(row), same(FullRowResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... getRowById is called
      val responseDto = rowService.getRowById(id);

      // THEN
      // ... RowResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createRow is called")
  public class WHEN_createRow {

    @Test
    @DisplayName("GIVEN CreateRowRequestDto and not exists by ordinal and rowClusterFk " +
      "... THEN FullRowResponseDto")
    public void GIVEN_CreateRowRequestDto_THEN_FullRowResponseDto() {
      // GIVEN
      // ... CreateRowRequestDto
      val requestDto = CreateRowRequestDtoMother.complete().build();
      // ... not exists by ordinal and rowClusterFk
      val ordinal = requestDto.ordinal();
      val rowClusterFk = requestDto.rowClusterFk();
      given(rowRepository.existsByOrdinalAndRowClusterId(same(ordinal), same(rowClusterFk))).willReturn(false);
      // ... ConversionService successfully converts from CreateRowRequestDto to Row
      val row = RowMother.complete().build();
      given(conversionService.convert(same(requestDto), same(Row.class))).willReturn(row);
      // ... RowRepository will successfully save Row
      given(rowRepository.save(same(row))).willReturn(row);
      // ... ConversionService successfully converts from Row to FullRowResponseDto
      val expectedResponseDto = FullRowResponseDtoMother.complete().build();
      given(conversionService.convert(same(row), same(FullRowResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... createRow is called
      val responseDto = rowService.createRow(requestDto);

      // THEN
      // ... RowResponseDto
      then(rowRepository).should(times(1)).save(same(row));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateRowById is called")
  public class WHEN_updateRowById {

    @Test
    @DisplayName("GIVEN id and UpdateRowRequestDto and not exists by ordinal and rowClusteFk" +
      "... THEN FullRowResponseDto is returned")
    public void GIVEN_idAndUpdateRowRequestDto_THEN_FullRowResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      val row = RowMother.complete().build();
      given(rowRepository.findById(same(id))).willReturn(Optional.of(row));
      // ... UpdateRowRequestDto
      val requestDto = UpdateRowRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = row.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... not exists by ordinal and rowClusterFk
      val ordinal = requestDto.ordinal();
      val rowClusterFk = requestDto.rowClusterFk();
      given(rowRepository.existsByOrdinalAndRowClusterId(same(ordinal), same(rowClusterFk))).willReturn(false);
      // ... RowMapper successfully partially updates Row with UpdateRowRequestDto
      given(rowMapper.partialUpdate(same(row), same(requestDto))).willReturn(row);
      // ... RowRepository successfully saves Row
      given(rowRepository.save(same(row))).willReturn(row);
      // ... ConversionService successfully converts from Row to FullRowResponseDto
      val expectedResponseDto = FullRowResponseDtoMother.complete().build();
      given(conversionService.convert(same(row), same(FullRowResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateRowById is called
      val responseDto = rowService.updateRowById(id, requestDto);

      // THEN
      // ... RowResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteRowById is called")
  public class WHEN_deleteRowById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN void")
    public void GIVEN_id_THEN_void() {
      // GIVEN
      // ... id
      val id = 1L;
      val row = RowMother.complete().build();
      given(rowRepository.findById(same(id))).willReturn(Optional.of(row));
      // ... CurrentUserService's logged-in User is not equal to Row User
      val loggedInUser = row.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... RowRepository returns empty set
      val ordinal = row.getOrdinal() + 1;
      val rowClusterFk = row.getRowCluster().getId();
      given(rowRepository.findByOrdinalGreaterThanEqualAndRowClusterIdOrderByOrdinalAsc(same(ordinal), same(rowClusterFk))).willReturn(Collections.emptySet());
      // ... RowRepository successfully deletes Row
      willDoNothing().given(rowRepository).delete(row);

      // WHEN
      rowService.deleteRowById(id);

      // THEN
      // ... void
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
      val id = 1L;
      given(rowRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> rowService.getById(id));

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
      val row = RowMother.complete().build();
      given(rowRepository.findById(same(id))).willReturn(Optional.of(row));
      // ... CurrentUserService's logged-in User is not equal to Row User
      val loggedInUser = UserMother.complete().id(row.getUser().getId() + 1).build();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getById is called
      when(() -> rowService.getById(id));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN Row is returned")
    public void GIVEN_id_THEN_Row() {
      // GIVEN
      // ... id
      val id = 1L;
      val expectedRow = RowMother.complete().build();
      given(rowRepository.findById(same(id))).willReturn(Optional.of(expectedRow));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = expectedRow.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getById is called
      val returnedRow = rowService.getById(id);

      // THEN
      // ... RowResponseDto is returned
      and.then(returnedRow)
        .isNotNull()
        .isEqualTo(expectedRow);
    }

  }

}