package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.CreateWorkRowRequestDtoToJoinedCreateWorkRowRequestDtoConverter;
import hr.algebra.fruity.converter.WorkRowToFullWorkRowResponseDtoConverter;
import hr.algebra.fruity.converter.WorkRowToWorkRowResponseDtoConverter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.WorkRowMapper;
import hr.algebra.fruity.model.WorkRow;
import hr.algebra.fruity.repository.RowRepository;
import hr.algebra.fruity.repository.WorkRowRepository;
import hr.algebra.fruity.service.impl.CurrentUserWorkRowService;
import hr.algebra.fruity.utils.mother.dto.CreateWorkRowRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullWorkRowResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedCreateWorkRowRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateWorkRowRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.RowMother;
import hr.algebra.fruity.utils.mother.model.WorkMother;
import hr.algebra.fruity.utils.mother.model.WorkRowMother;
import hr.algebra.fruity.validator.JoinedCreateWorkRowRequestDtoValidator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.googlecode.catchexception.apis.BDDCatchException.caughtException;
import static com.googlecode.catchexception.apis.BDDCatchException.when;
import static org.assertj.core.api.BDDAssertions.and;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
@DisplayName("WorkRow Service Unit Test")
@SuppressWarnings("static-access")
public class WorkRowServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserWorkRowService workRowService;

  @Mock
  private WorkRowToWorkRowResponseDtoConverter toWorkRowResponseDtoConverter;

  @Mock
  private WorkRowToFullWorkRowResponseDtoConverter toFullWorkRowResponseDtoConverter;

  @Mock
  private CreateWorkRowRequestDtoToJoinedCreateWorkRowRequestDtoConverter toJoinedCreateWorkRowRequestDtoConverter;

  @Mock
  private JoinedCreateWorkRowRequestDtoValidator joinedCreateWorkRowRequestDtoValidator;

  @Mock
  private WorkRowMapper workRowMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private WorkRowRepository workRowRepository;

  @Mock
  private RowRepository rowRepository;

  @Mock
  private WorkService workService;

  @Nested
  @DisplayName("WHEN getWorkRowByWorkIdAndRowId is called")
  public class WHEN_getWorkRowByWorkIdAndRowId {

    @Test
    @DisplayName("GIVEN workId and rowId " +
      "... THEN WorkRowResponseDto is returned")
    public void GIVEN_ids_THEN_WorkRowResponseDto() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val rowId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkRowRepository successfully finds
      val workRow = WorkRowMother.complete().build();
      given(workRowRepository.findByWorkIdAndRowIdAndWorkUserId(same(workId), same(rowId), same(userId))).willReturn(Optional.of(workRow));
      // ... WorkRowToFullWorkRowResponseDtoConverter successfully converts
      val expectedResponseDto = FullWorkRowResponseDtoMother.complete().build();
      given(toFullWorkRowResponseDtoConverter.convert(same(workRow))).willReturn(expectedResponseDto);

      // WHEN
      // ... getWorkRowByWorkIdAndRowId is called
      val responseDto = workRowService.getWorkRowByWorkIdAndRowId(workId, rowId);

      // THEN
      // ... FullWorkRowResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createWorkRowForWorkId is called")
  public class WHEN_createWorkRowForWorkId {

    @Captor
    ArgumentCaptor<Set<WorkRow>> setOfWorkRowCaptor;

    @Test
    @DisplayName("GIVEN workId and CreateWorkRowRequestDto " +
      "... THEN WorkRowResponseDto")
    public void GIVEN_LongAndCreateWorkRowRequestDto_THEN_WorkRowResponseDto() {
      // GIVEN
      // ... workId
      val workId = 1L;
      // ... CreateWorkRowRequestDto
      val requestDto = CreateWorkRowRequestDtoMother.complete().build();
      // ... WorkService successfully gets
      val work = WorkMother.complete().build();
      given(workService.getById(same(workId))).willReturn(work);
      // ... CreateWorkRowRequestDtoToJoinedCreateWorkRowRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedCreateWorkRowRequestDtoMother.complete().rows(new HashSet<>(Set.of(RowMother.complete().id(1L).build(), RowMother.complete().id(3L).build()))).build();
      given(toJoinedCreateWorkRowRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... WorkRowRepository successfully finds
      val existingRows = List.of(WorkRowMother.complete().row(RowMother.complete().id(3L).build()).build(), WorkRowMother.complete().row(RowMother.complete().id(7L).build()).build());
      given(workRowRepository.findAllByWork(same(work))).willReturn(existingRows);
      // ... WorkRowRepository will successfully save WorkRow
      given(workRowRepository.saveAll(setOfWorkRowCaptor.capture())).willReturn(List.of());

      // WHEN
      // ... createWorkRowForWorkId is called
      workRowService.createWorkRowForWorkId(workId, requestDto);

      // THEN
      // ... WorkRowResponseDto
      and.then(setOfWorkRowCaptor.getValue())
        .isNotNull()
        .hasSize(1);
    }

  }

  @Nested
  @DisplayName("WHEN updateWorkRowByWorkIdAndRowId is called")
  public class WHEN_updateWorkRowByWorkIdAndRowId {

    @Test
    @DisplayName("GIVEN workId, rowId, and UpdateWorkRowRequestDto " +
      "... THEN WorkRowResponseDto is returned")
    public void GIVEN_idsAndUpdateWorkRowRequestDto_THEN_WorkRowResponseDto() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val rowId = 1L;
      // ... UpdateWorkRowRequestDto
      val requestDto = UpdateWorkRowRequestDtoMother.complete().build();
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkRowRepository successfully finds
      val workRow = WorkRowMother.complete().build();
      given(workRowRepository.findByWorkIdAndRowIdAndWorkUserId(same(workId), same(rowId), same(userId))).willReturn(Optional.of(workRow));
      // ... WorkRowMapper successfully partially updates WorkRow with UpdateWorkRowRequestDto
      given(workRowMapper.partialUpdate(same(workRow), same(requestDto))).willReturn(workRow);
      // ... WorkRowRepository successfully saves WorkRow
      given(workRowRepository.save(same(workRow))).willReturn(workRow);
      // ... WorkRowToFullWorkRowResponseDtoConverter successfully converts
      val expectedResponseDto = FullWorkRowResponseDtoMother.complete().build();
      given(toFullWorkRowResponseDtoConverter.convert(same(workRow))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateWorkRowByWorkIdAndRowId is called
      val responseDto = workRowService.updateWorkRowByWorkIdAndRowId(workId, rowId, requestDto);

      // THEN
      // ... WorkRowResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteWorkRowByWorkIdAndRowId is called")
  public class WHEN_deleteWorkRowByWorkIdAndRowId {

    @Test
    @DisplayName("GIVEN workId and rowId " +
      "... THEN void")
    public void GIVEN_ids_THEN_void() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val rowId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkRowRepository successfully finds
      val workRow = WorkRowMother.complete().build();
      given(workRowRepository.findByWorkIdAndRowIdAndWorkUserId(same(workId), same(rowId), same(userId))).willReturn(Optional.of(workRow));
      // ... WorkRowRepository successfully deletes WorkRow
      willDoNothing().given(workRowRepository).delete(workRow);

      // WHEN
      // ... deleteWorkRowByWorkIdAndRowId is called
      workRowService.deleteWorkRowByWorkIdAndRowId(workId, rowId);

      // THEN
      // ... void
    }

  }

  @Nested
  @DisplayName("WHEN getById is called")
  public class WHEN_getById {

    @Test
    @DisplayName("GIVEN invalid workId and rowId " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidIds_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val rowId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkRowRepository fails to find
      given(workRowRepository.findByWorkIdAndRowIdAndWorkUserId(same(workId), same(rowId), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> workRowService.getByWorkIdAndRowId(workId, rowId));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN workId and rowId " +
      "... THEN WorkRow is returned")
    public void GIVEN_ids_THEN_WorkRow() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val rowId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkRowRepository successfully finds
      val expectedWorkRow = WorkRowMother.complete().build();
      given(workRowRepository.findByWorkIdAndRowIdAndWorkUserId(same(workId), same(rowId), same(userId))).willReturn(Optional.of(expectedWorkRow));

      // WHEN
      // ... getById is called
      val returnedWorkRow = workRowService.getByWorkIdAndRowId(workId, rowId);

      // THEN
      // ... WorkRowResponseDto is returned
      and.then(returnedWorkRow)
        .isNotNull()
        .isEqualTo(expectedWorkRow);
    }

  }

}