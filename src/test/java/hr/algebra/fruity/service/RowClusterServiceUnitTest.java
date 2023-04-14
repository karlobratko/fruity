package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.FullRowClusterResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.RowClusterMapper;
import hr.algebra.fruity.model.RowCluster;
import hr.algebra.fruity.repository.RowClusterRepository;
import hr.algebra.fruity.service.impl.CurrentUserRowClusterService;
import hr.algebra.fruity.utils.mother.dto.CreateRowClusterRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullRowClusterResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateRowClusterRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.RowClusterMother;
import hr.algebra.fruity.utils.mother.model.UserMother;
import hr.algebra.fruity.validator.RowClusterWithUpdateRowClusterRequestDtoValidator;
import hr.algebra.fruity.validator.CreateRowClusterRequestDtoValidator;
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
@DisplayName("RowCluster Unit Test")
@SuppressWarnings("static-access")
public class RowClusterServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserRowClusterService rowClusterService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private CreateRowClusterRequestDtoValidator createRowClusterRequestDtoValidator;

  @Mock
  private RowClusterWithUpdateRowClusterRequestDtoValidator rowClusterWithUpdateRowClusterRequestDtoValidator;

  @Mock
  private RowClusterMapper rowClusterMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private RowClusterRepository rowClusterRepository;

  @Nested
  @DisplayName("WHEN getRowClusterById is called")
  public class WHEN_getRowClusterById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN FullRowClusterResponseDto is returned")
    public void GIVEN_id_THEN_FullRowClusterResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      val rowCluster = RowClusterMother.complete().build();
      given(rowClusterRepository.findById(same(id))).willReturn(Optional.of(rowCluster));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = rowCluster.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... ConversionService successfully converts from User to FullRowClusterResponseDto
      val expectedResponseDto = FullRowClusterResponseDtoMother.complete().build();
      given(conversionService.convert(same(rowCluster), same(FullRowClusterResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... getRowClusterById is called
      val responseDto = rowClusterService.getRowClusterById(id);

      // THEN
      // ... RowClusterResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createRowCluster is called")
  public class WHEN_createRowCluster {

    @Test
    @DisplayName("GIVEN CreateRowClusterRequestDto " +
      "... THEN FullRowClusterResponseDto")
    public void GIVEN_CreateRowClusterRequestDto_THEN_FullRowClusterResponseDto() {
      // GIVEN
      // ... CreateRowClusterRequestDto
      val requestDto = CreateRowClusterRequestDtoMother.complete().build();
      // CreateRowClusterRequestDtoValidator successfully validates CreateRowClusterRequestDto
      willDoNothing().given(createRowClusterRequestDtoValidator).validate(same(requestDto));
      // ... ConversionService successfully converts from CreateRowClusterRequestDto to RowCluster
      val rowCluster = RowClusterMother.complete().build();
      given(conversionService.convert(same(requestDto), same(RowCluster.class))).willReturn(rowCluster);
      // ... RowClusterRepository will successfully save RowCluster
      given(rowClusterRepository.save(same(rowCluster))).willReturn(rowCluster);
      // ... ConversionService successfully converts from RowCluster to FullRowClusterResponseDto
      val expectedResponseDto = FullRowClusterResponseDtoMother.complete().build();
      given(conversionService.convert(same(rowCluster), same(FullRowClusterResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... createRowCluster is called
      val responseDto = rowClusterService.createRowCluster(requestDto);

      // THEN
      // ... RowClusterResponseDto
      then(rowClusterRepository).should(times(1)).save(same(rowCluster));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateRowClusterById is called")
  public class WHEN_updateRowClusterById {

    @Test
    @DisplayName("GIVEN id and UpdateRowClusterRequestDto " +
      "... THEN FullRowClusterResponseDto is returned")
    public void GIVEN_idAndUpdateRowClusterRequestDto_THEN_FullRowClusterResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      val rowCluster = RowClusterMother.complete().build();
      given(rowClusterRepository.findById(same(id))).willReturn(Optional.of(rowCluster));
      // ... UpdateRowClusterRequestDto
      val requestDto = UpdateRowClusterRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = rowCluster.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... RowClusterWithUpdateRowClusterRequestDtoValidator successfully validates RowCluster with UpdateRowClusterRequestDto
      willDoNothing().given(rowClusterWithUpdateRowClusterRequestDtoValidator).validate(same(rowCluster), same(requestDto));
      // ... RowClusterMapper successfully partially updates RowCluster with UpdateRowClusterRequestDto
      given(rowClusterMapper.partialUpdate(same(rowCluster), same(requestDto))).willReturn(rowCluster);
      // ... RowClusterRepository successfully saves RowCluster
      given(rowClusterRepository.save(same(rowCluster))).willReturn(rowCluster);
      // ... ConversionService successfully converts from RowCluster to FullRowClusterResponseDto
      val expectedResponseDto = FullRowClusterResponseDtoMother.complete().build();
      given(conversionService.convert(same(rowCluster), same(FullRowClusterResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateRowClusterById is called
      val responseDto = rowClusterService.updateRowClusterById(id, requestDto);

      // THEN
      // ... RowClusterResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteRowClusterById is called")
  public class WHEN_deleteRowClusterById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN void")
    public void GIVEN_id_THEN_void() {
      // GIVEN
      // ... id
      val id = 1L;
      val rowCluster = RowClusterMother.complete().build();
      given(rowClusterRepository.findById(same(id))).willReturn(Optional.of(rowCluster));
      // ... CurrentUserService's logged-in User is not equal to RowCluster User
      val loggedInUser = rowCluster.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... RowClusterRepository successfully deletes RowCluster
      willDoNothing().given(rowClusterRepository).delete(rowCluster);

      // WHEN
      rowClusterService.deleteRowClusterById(id);

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
      given(rowClusterRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> rowClusterService.getById(id));

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
      val rowCluster = RowClusterMother.complete().build();
      given(rowClusterRepository.findById(same(id))).willReturn(Optional.of(rowCluster));
      // ... CurrentUserService's logged-in User is not equal to RowCluster User
      val loggedInUser = UserMother.complete().id(rowCluster.getUser().getId() + 1).build();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getById is called
      when(() -> rowClusterService.getById(id));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN RowCluster is returned")
    public void GIVEN_id_THEN_RowCluster() {
      // GIVEN
      // ... id
      val id = 1L;
      val expectedRowCluster = RowClusterMother.complete().build();
      given(rowClusterRepository.findById(same(id))).willReturn(Optional.of(expectedRowCluster));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = expectedRowCluster.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getById is called
      val returnedRowCluster = rowClusterService.getById(id);

      // THEN
      // ... RowClusterResponseDto is returned
      and.then(returnedRowCluster)
        .isNotNull()
        .isEqualTo(expectedRowCluster);
    }

  }

}