package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.CreateRowClusterRequestDtoToJoinedCreateRowClusterRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateRowClusterRequestDtoToRowClusterConverter;
import hr.algebra.fruity.converter.RowClusterToFullRowClusterResponseDtoConverter;
import hr.algebra.fruity.converter.RowClusterToRowClusterResponseDtoConverter;
import hr.algebra.fruity.converter.RowToRowResponseDtoConverter;
import hr.algebra.fruity.converter.UpdateRowClusterRequestDtoToJoinedUpdateRowClusterRequestDtoConverter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.RowClusterMapper;
import hr.algebra.fruity.repository.RowClusterRepository;
import hr.algebra.fruity.repository.RowRepository;
import hr.algebra.fruity.service.impl.CurrentUserRowClusterService;
import hr.algebra.fruity.utils.mother.dto.CreateRowClusterRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullRowClusterResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedCreateRowClusterRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedUpdateRowClusterRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateRowClusterRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.RowClusterMother;
import hr.algebra.fruity.validator.JoinedCreateRowClusterRequestDtoValidator;
import hr.algebra.fruity.validator.RowClusterWithJoinedUpdateRowClusterRequestDtoValidator;
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
  private RowClusterToRowClusterResponseDtoConverter toRowClusterResponseDtoConverter;

  @Mock
  private RowClusterToFullRowClusterResponseDtoConverter toFullRowClusterResponseDtoConverter;

  @Mock
  private CreateRowClusterRequestDtoToJoinedCreateRowClusterRequestDtoConverter toJoinedCreateRowClusterRequestDtoConverter;

  @Mock
  private JoinedCreateRowClusterRequestDtoToRowClusterConverter fromJoinedCreateRowClusterRequestDtoConverter;

  @Mock
  private UpdateRowClusterRequestDtoToJoinedUpdateRowClusterRequestDtoConverter toJoinedUpdateRowClusterRequestDtoConverter;

  @Mock
  private RowToRowResponseDtoConverter toRowResponseDtoConverter;

  @Mock
  private JoinedCreateRowClusterRequestDtoValidator joinedCreateRowClusterRequestDtoValidator;

  @Mock
  private RowClusterWithJoinedUpdateRowClusterRequestDtoValidator rowClusterWithJoinedUpdateRowClusterRequestDtoValidator;

  @Mock
  private RowClusterMapper rowClusterMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private RowClusterRepository rowClusterRepository;

  @Mock
  private RowRepository rowRepository;

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
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RowClusterRepository fails to findByIdAndUserId
      val rowCluster = RowClusterMother.complete().build();
      given(rowClusterRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(rowCluster));
      // ... RowClusterToFullRowClusterResponseDtoConverter successfully converts
      val expectedResponseDto = FullRowClusterResponseDtoMother.complete().build();
      given(toFullRowClusterResponseDtoConverter.convert(same(rowCluster))).willReturn(expectedResponseDto);

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
      // ... CreateRowClusterRequestDtoToJoinedCreateRowClusterRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedCreateRowClusterRequestDtoMother.complete().build();
      given(toJoinedCreateRowClusterRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... JoinedCreateRowClusterRequestDtoValidator successfully validates
      willDoNothing().given(joinedCreateRowClusterRequestDtoValidator).validate(same(joinedRequestDto));
      // ... JoinedCreateRowClusterRequestDtoToRowClusterConverter successfully converts
      val rowCluster = RowClusterMother.complete().build();
      given(fromJoinedCreateRowClusterRequestDtoConverter.convert(same(joinedRequestDto))).willReturn(rowCluster);
      // ... RowClusterRepository will successfully save
      given(rowClusterRepository.save(same(rowCluster))).willReturn(rowCluster);
      // ... RowClusterToFullRowClusterResponseDtoConverter successfully converts
      val expectedResponseDto = FullRowClusterResponseDtoMother.complete().build();
      given(toFullRowClusterResponseDtoConverter.convert(same(rowCluster))).willReturn(expectedResponseDto);

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
      // ... UpdateRowClusterRequestDto
      val requestDto = UpdateRowClusterRequestDtoMother.complete().build();
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RowClusterRepository fails to findByIdAndUserId
      val rowCluster = RowClusterMother.complete().build();
      given(rowClusterRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(rowCluster));
      // ... UpdateRowClusterRequestDtoToJoinedUpdateRowClusterRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedUpdateRowClusterRequestDtoMother.complete().build();
      given(toJoinedUpdateRowClusterRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... RowClusterWithJoinedUpdateRowClusterRequestDtoValidator successfully validates
      willDoNothing().given(rowClusterWithJoinedUpdateRowClusterRequestDtoValidator).validate(same(rowCluster), same(joinedRequestDto));
      // ... RowClusterMapper successfully partially updates
      given(rowClusterMapper.partialUpdate(same(rowCluster), same(joinedRequestDto))).willReturn(rowCluster);
      // ... RowClusterRepository successfully saves
      given(rowClusterRepository.save(same(rowCluster))).willReturn(rowCluster);
      // ... RowClusterToFullRowClusterResponseDtoConverter successfully converts
      val expectedResponseDto = FullRowClusterResponseDtoMother.complete().build();
      given(toFullRowClusterResponseDtoConverter.convert(same(rowCluster))).willReturn(expectedResponseDto);

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
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RowClusterRepository fails to findByIdAndUserId
      val rowCluster = RowClusterMother.complete().build();
      given(rowClusterRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(rowCluster));
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
    @DisplayName("GIVEN invalid id or userId " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidIdOrUserId_THEN_EntityNotFoundException() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RowClusterRepository fails to findByIdAndUserId
      given(rowClusterRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> rowClusterService.getById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN RowCluster is returned")
    public void GIVEN_id_THEN_RowCluster() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RowClusterRepository fails to findByIdAndUserId
      val expectedRowCluster = RowClusterMother.complete().build();
      given(rowClusterRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(expectedRowCluster));

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