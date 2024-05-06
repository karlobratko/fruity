package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.CreateRowRequestDtoToJoinedCreateRowRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateRowRequestDtoToRowConverter;
import hr.algebra.fruity.converter.RowToFullRowResponseDtoConverter;
import hr.algebra.fruity.converter.RowToRowResponseDtoConverter;
import hr.algebra.fruity.converter.UpdateRowRequestDtoToJoinedUpdateRowRequestDtoConverter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.RowMapper;
import hr.algebra.fruity.repository.RealisationRowRepository;
import hr.algebra.fruity.repository.RowRepository;
import hr.algebra.fruity.repository.WorkRowRepository;
import hr.algebra.fruity.service.impl.CurrentUserRowService;
import hr.algebra.fruity.utils.mother.dto.CreateRowRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullRowResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedCreateRowRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedUpdateRowRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateRowRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.RowMother;
import jakarta.persistence.EntityManager;
import java.util.Collections;
import java.util.Optional;
import lombok.val;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("Row Service Unit Test")
@SuppressWarnings("static-access")
public class RowServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserRowService rowService;

  @Mock
  private RowToRowResponseDtoConverter toRowResponseDtoConverter;

  @Mock
  private RowToFullRowResponseDtoConverter toFullRowResponseDtoConverter;

  @Mock
  private CreateRowRequestDtoToJoinedCreateRowRequestDtoConverter toJoinedCreateRowRequestDtoConverter;

  @Mock
  private JoinedCreateRowRequestDtoToRowConverter fromJoinedCreateRowRequestDtoConverter;

  @Mock
  private UpdateRowRequestDtoToJoinedUpdateRowRequestDtoConverter toJoinedUpdateRowRequestDtoConverter;

  @Mock
  private RowMapper rowMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private RowRepository rowRepository;

  @Mock
  private RowClusterService rowClusterService;

  @Mock
  private ArcodeParcelService arcodeParcelService;

  @Mock
  private CadastralParcelService cadastralParcelService;

  @Mock
  private WorkRowRepository workRowRepository;

  @Mock
  private RealisationRowRepository realisationRowRepository;

  @Mock
  private EntityManager entityManager;

  @BeforeEach
  public void beforeEach() {
    // ... EntityManager successfully returns session
    given(entityManager.unwrap(same(Session.class))).willReturn(mock(Session.class));
  }


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
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RowRepository fails to findByIdAndUserId
      val row = RowMother.complete().build();
      given(rowRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(row));
      // ... RowToFullRowResponseDtoConverter successfully converts
      val expectedResponseDto = FullRowResponseDtoMother.complete().build();
      given(toFullRowResponseDtoConverter.convert(same(row))).willReturn(expectedResponseDto);

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
      // ... CreateRowRequestDtoToJoinedCreateRowRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedCreateRowRequestDtoMother.complete().build();
      given(toJoinedCreateRowRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... not exists by ordinal and rowClusterFk
      given(rowRepository.existsByOrdinalAndRowCluster(same(joinedRequestDto.ordinal()), same(joinedRequestDto.rowCluster()))).willReturn(false);
      // ... JoinedCreateRowRequestDtoToRowConverter successfully converts
      val row = RowMother.complete().build();
      given(fromJoinedCreateRowRequestDtoConverter.convert(same(joinedRequestDto))).willReturn(row);
      // ... RowRepository successfully saves
      given(rowRepository.save(same(row))).willReturn(row);
      // ... RowToFullRowResponseDtoConverter successfully converts
      val expectedResponseDto = FullRowResponseDtoMother.complete().build();
      given(toFullRowResponseDtoConverter.convert(same(row))).willReturn(expectedResponseDto);

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
      // ... UpdateRowRequestDto
      val requestDto = UpdateRowRequestDtoMother.complete().build();
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RowRepository successfully finds
      val row = RowMother.complete().build();
      given(rowRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(row));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = row.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... UpdateRowRequestDtoToJoinedUpdateRowRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedUpdateRowRequestDtoMother.complete().build();
      given(toJoinedUpdateRowRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... not exists by ordinal and rowClusterFk
      given(rowRepository.existsByOrdinalAndRowCluster(same(joinedRequestDto.ordinal()), same(row.getRowCluster()))).willReturn(false);
      // ... RowMapper successfully partially updates
      given(rowMapper.partialUpdate(same(row), same(joinedRequestDto))).willReturn(row);
      // ... RowRepository successfully saves
      given(rowRepository.save(same(row))).willReturn(row);
      // ... RowToFullRowResponseDtoConverter successfully converts
      val expectedResponseDto = FullRowResponseDtoMother.complete().build();
      given(toFullRowResponseDtoConverter.convert(same(row))).willReturn(expectedResponseDto);

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
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RowRepository successfully finds
      val row = RowMother.complete().build();
      given(rowRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(row));
      // ... RowRepository returns empty set
      val ordinal = row.getOrdinal() + 1;
      given(rowRepository.findByOrdinalGreaterThanEqualAndRowClusterOrderByOrdinalAsc(same(ordinal), same(row.getRowCluster()))).willReturn(Collections.emptySet());
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
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RowRepository fails to findByIdAndUserId
      given(rowRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> rowService.getById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN Row is returned")
    public void GIVEN_id_THEN_Row() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RowRepository fails to findByIdAndUserId
      val expectedRow = RowMother.complete().build();
      given(rowRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(expectedRow));

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