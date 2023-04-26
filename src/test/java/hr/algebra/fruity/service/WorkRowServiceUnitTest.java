package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.FullWorkRowResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.exception.NoNewRowsForWorkRowCreationException;
import hr.algebra.fruity.mapper.WorkRowMapper;
import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.model.WorkRow;
import hr.algebra.fruity.repository.RowRepository;
import hr.algebra.fruity.repository.WorkRowRepository;
import hr.algebra.fruity.service.impl.CurrentUserWorkRowService;
import hr.algebra.fruity.utils.mother.dto.CreateWorkRowRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullWorkRowResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateWorkRowRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.RowMother;
import hr.algebra.fruity.utils.mother.model.UserMother;
import hr.algebra.fruity.utils.mother.model.WorkMother;
import hr.algebra.fruity.utils.mother.model.WorkRowMother;
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
import org.springframework.core.convert.ConversionService;

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
  private ConversionService conversionService;

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
    @DisplayName("GIVEN workId and attachmentId " +
      "... THEN WorkRowResponseDto is returned")
    public void GIVEN_ids_THEN_WorkRowResponseDto() {
      // GIVEN
      // ... ids
      val workId = 1L;
      val attachmentId = 1L;
      val workRow = WorkRowMother.complete().build();
      given(workRowRepository.findByWorkIdAndRowId(same(workId), same(attachmentId))).willReturn(Optional.of(workRow));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = workRow.getWork().getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... ConversionService successfully converts from User to FullWorkRowResponseDto
      val expectedResponseDto = FullWorkRowResponseDtoMother.complete().build();
      given(conversionService.convert(same(workRow), same(FullWorkRowResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... getWorkRowByWorkIdAndRowId is called
      val responseDto = workRowService.getWorkRowByWorkIdAndRowId(workId, attachmentId);

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
    @DisplayName("GIVEN workId and CreateWorkRowRequestDto with no Rows " +
      "... THEN NoNewRowsForWorkRowCreationException is thrown")
    public void GIVEN_LongAndCreateWorkRowRequestDtoNoRows_THEN_NoNewRowsForWorkRowCreationException() {
      // GIVEN
      // ... workId
      val workId = 1L;
      // ... CreateWorkRowRequestDto
      val requestDto = CreateWorkRowRequestDtoMother.complete()
        .rowFk(1L)
        .rowFks(List.of(2L, 3L))
        .build();
      // ... WorkService successfully gets Work by id
      val work = WorkMother.complete().build();
      given(workService.getById(same(workId))).willReturn(work);
      // ... RowRepository successfully finds all Rows
      given(rowRepository.findById(same(requestDto.rowFk()))).willReturn(Optional.empty());
      List<Row> rows = List.of();
      given(rowRepository.findAllById(same(requestDto.rowFks()))).willReturn(rows);
      Set<Row> rowsFromRowCluster = Set.of();
      given(rowRepository.findAllByRowClusterId(same(requestDto.rowClusterFk()))).willReturn(rowsFromRowCluster);
      Set<Row> rowsFromArcodeParcel = Set.of();
      given(rowRepository.findAllByRowClusterArcodeParcelId(same(requestDto.arcodeParcelFk()))).willReturn(rowsFromArcodeParcel);
      Set<Row> rowsFromCadastralParcel = Set.of();
      given(rowRepository.findAllByRowClusterArcodeParcelCadastralParcelId(same(requestDto.cadastralParcelFk()))).willReturn(rowsFromCadastralParcel);
      // ... WorkRowRepository successfully finds all WorkRows by workId
      val existingRows = List.of(WorkRowMother.complete().row(RowMother.complete().id(1L).build()).build(), WorkRowMother.complete().row(RowMother.complete().id(2L).build()).build());
      given(workRowRepository.findAllByWorkId(same(work.getId()))).willReturn(existingRows);

      // WHEN
      // ... createWorkRowForWorkId is called
      when(() -> workRowService.createWorkRowForWorkId(workId, requestDto));

      // THEN
      // ... NoNewRowsForWorkRowCreationException is thrown
      and.then(caughtException())
        .isInstanceOf(NoNewRowsForWorkRowCreationException.class)
        .hasMessage(NoNewRowsForWorkRowCreationException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN workId and CreateWorkRowRequestDto with existing Rows " +
      "... THEN NoNewRowsForWorkRowCreationException is thrown")
    public void GIVEN_LongAndCreateWorkRowRequestDtoExistingRows_THEN_NoNewRowsForWorkRowCreationException() {
      // GIVEN
      // ... workId
      val workId = 1L;
      // ... CreateWorkRowRequestDto
      val requestDto = CreateWorkRowRequestDtoMother.complete()
        .rowFk(1L)
        .rowFks(List.of(2L, 3L))
        .build();
      // ... WorkService successfully gets Work by id
      val work = WorkMother.complete().build();
      given(workService.getById(same(workId))).willReturn(work);
      // ... RowRepository successfully finds all Rows
      val row = RowMother.complete().id(1L).build();
      given(rowRepository.findById(same(requestDto.rowFk()))).willReturn(Optional.of(row));
      val rows = List.of(RowMother.complete().id(2L).build());
      given(rowRepository.findAllById(same(requestDto.rowFks()))).willReturn(rows);
      Set<Row> rowsFromRowCluster = Set.of();
      given(rowRepository.findAllByRowClusterId(same(requestDto.rowClusterFk()))).willReturn(rowsFromRowCluster);
      Set<Row> rowsFromArcodeParcel = Set.of();
      given(rowRepository.findAllByRowClusterArcodeParcelId(same(requestDto.arcodeParcelFk()))).willReturn(rowsFromArcodeParcel);
      Set<Row> rowsFromCadastralParcel = Set.of();
      given(rowRepository.findAllByRowClusterArcodeParcelCadastralParcelId(same(requestDto.cadastralParcelFk()))).willReturn(rowsFromCadastralParcel);
      // ... WorkRowRepository successfully finds all WorkRows by workId
      val existingRows = List.of(WorkRowMother.complete().row(RowMother.complete().id(1L).build()).build(), WorkRowMother.complete().row(RowMother.complete().id(2L).build()).build());
      given(workRowRepository.findAllByWorkId(same(work.getId()))).willReturn(existingRows);

      // WHEN
      // ... createWorkRowForWorkId is called
      when(() -> workRowService.createWorkRowForWorkId(workId, requestDto));

      // THEN
      // ... NoNewRowsForWorkRowCreationException is thrown
      and.then(caughtException())
        .isInstanceOf(NoNewRowsForWorkRowCreationException.class)
        .hasMessage(NoNewRowsForWorkRowCreationException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }


    @Test
    @DisplayName("GIVEN workId and CreateWorkRowRequestDto " +
      "... THEN WorkRowResponseDto")
    public void GIVEN_LongAndCreateWorkRowRequestDto_THEN_WorkRowResponseDto() {
      // GIVEN
      // ... workId
      val workId = 1L;
      // ... CreateWorkRowRequestDto
      val requestDto = CreateWorkRowRequestDtoMother.complete()
        .rowFk(1L)
        .rowFks(List.of(2L, 3L))
        .build();
      // ... WorkService successfully gets Work by id
      val work = WorkMother.complete().build();
      given(workService.getById(same(workId))).willReturn(work);
      // ... RowRepository successfully finds all Rows
      val row = RowMother.complete().id(1L).build();
      given(rowRepository.findById(same(requestDto.rowFk()))).willReturn(Optional.of(row));
      val rows = List.of(RowMother.complete().id(2L).build(), RowMother.complete().id(3L).build());
      given(rowRepository.findAllById(same(requestDto.rowFks()))).willReturn(rows);
      val rowsFromRowCluster = Set.of(RowMother.complete().id(3L).build(), RowMother.complete().id(4L).build());
      given(rowRepository.findAllByRowClusterId(same(requestDto.rowClusterFk()))).willReturn(rowsFromRowCluster);
      val rowsFromArcodeParcel = Set.of(RowMother.complete().id(4L).build(), RowMother.complete().id(5L).build());
      given(rowRepository.findAllByRowClusterArcodeParcelId(same(requestDto.arcodeParcelFk()))).willReturn(rowsFromArcodeParcel);
      val rowsFromCadastralParcel = Set.of(RowMother.complete().id(6L).build(), RowMother.complete().id(7L).build());
      given(rowRepository.findAllByRowClusterArcodeParcelCadastralParcelId(same(requestDto.cadastralParcelFk()))).willReturn(rowsFromCadastralParcel);
      // ... WorkRowRepository successfully finds all WorkRows by workId
      val existingRows = List.of(WorkRowMother.complete().row(RowMother.complete().id(3L).build()).build(), WorkRowMother.complete().row(RowMother.complete().id(7L).build()).build());
      given(workRowRepository.findAllByWorkId(same(work.getId()))).willReturn(existingRows);
      // ... WorkRowRepository will successfully save WorkRow
      given(workRowRepository.saveAll(setOfWorkRowCaptor.capture())).willReturn(List.of());

      // WHEN
      // ... createWorkRowForWorkId is called
      workRowService.createWorkRowForWorkId(workId, requestDto);

      // THEN
      // ... WorkRowResponseDto
      and.then(setOfWorkRowCaptor.getValue())
        .isNotNull()
        .hasSize(5);
    }

  }

  @Nested
  @DisplayName("WHEN updateWorkRowByWorkIdAndRowId is called")
  public class WHEN_updateWorkRowByWorkIdAndRowId {

    @Test
    @DisplayName("GIVEN workId, attachmentId, and UpdateWorkRowRequestDto " +
      "... THEN WorkRowResponseDto is returned")
    public void GIVEN_idsAndUpdateWorkRowRequestDto_THEN_WorkRowResponseDto() {
      // GIVEN
      // ... ids
      val workId = 1L;
      val attachmentId = 1L;
      val workRow = WorkRowMother.complete().build();
      given(workRowRepository.findByWorkIdAndRowId(same(workId), same(attachmentId))).willReturn(Optional.of(workRow));
      // ... UpdateWorkRowRequestDto
      val requestDto = UpdateWorkRowRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = workRow.getWork().getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... WorkRowMapper successfully partially updates WorkRow with UpdateWorkRowRequestDto
      given(workRowMapper.partialUpdate(same(workRow), same(requestDto))).willReturn(workRow);
      // ... WorkRowRepository successfully saves WorkRow
      given(workRowRepository.save(same(workRow))).willReturn(workRow);
      // ... ConversionService successfully converts from WorkRow to FullWorkRowResponseDto
      val expectedResponseDto = FullWorkRowResponseDtoMother.complete().build();
      given(conversionService.convert(same(workRow), same(FullWorkRowResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateWorkRowByWorkIdAndRowId is called
      val responseDto = workRowService.updateWorkRowByWorkIdAndRowId(workId, attachmentId, requestDto);

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
    @DisplayName("GIVEN workId and attachmentId " +
      "... THEN void")
    public void GIVEN_ids_THEN_void() {
      // GIVEN
      // ... ids
      val workId = 1L;
      val attachmentId = 1L;
      val workRow = WorkRowMother.complete().build();
      given(workRowRepository.findByWorkIdAndRowId(same(workId), same(attachmentId))).willReturn(Optional.of(workRow));
      // ... CurrentUserService's logged-in User is not equal to WorkRow User
      val loggedInUser = workRow.getWork().getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... WorkRowRepository successfully deletes WorkRow
      willDoNothing().given(workRowRepository).delete(workRow);

      // WHEN
      // ... deleteWorkRowByWorkIdAndRowId is called
      workRowService.deleteWorkRowByWorkIdAndRowId(workId, attachmentId);

      // THEN
      // ... void
    }

  }

  @Nested
  @DisplayName("WHEN getById is called")
  public class WHEN_getById {

    @Test
    @DisplayName("GIVEN invalid workId and attachmentId " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidIds_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val attachmentId = 1L;
      given(workRowRepository.findByWorkIdAndRowId(same(workId), same(attachmentId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> workRowService.getByWorkIdAndRowId(workId, attachmentId));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(EntityNotFoundException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN workId, attachmentId, and foreign logged-in User " +
      "... THEN ForeignUserDataAccessException is thrown")
    public void GIVEN_idsAndForeignUser_THEN_ForeignUserDataAccessException() {
      // GIVEN
      // ... ids
      val workId = 1L;
      val attachmentId = 1L;
      val workRow = WorkRowMother.complete().build();
      given(workRowRepository.findByWorkIdAndRowId(same(workId), same(attachmentId))).willReturn(Optional.of(workRow));
      // ... CurrentUserService's logged-in User is not equal to WorkRow User
      val loggedInUser = UserMother.complete().id(workRow.getWork().getUser().getId() + 1).build();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getById is called
      when(() -> workRowService.getByWorkIdAndRowId(workId, attachmentId));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN workId and attachmentId " +
      "... THEN WorkRow is returned")
    public void GIVEN_ids_THEN_WorkRow() {
      // GIVEN
      // ... ids
      val workId = 1L;
      val attachmentId = 1L;
      val expectedWorkRow = WorkRowMother.complete().build();
      given(workRowRepository.findByWorkIdAndRowId(same(workId), same(attachmentId))).willReturn(Optional.of(expectedWorkRow));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = expectedWorkRow.getWork().getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getById is called
      val returnedWorkRow = workRowService.getByWorkIdAndRowId(workId, attachmentId);

      // THEN
      // ... WorkRowResponseDto is returned
      and.then(returnedWorkRow)
        .isNotNull()
        .isEqualTo(expectedWorkRow);
    }

  }

}