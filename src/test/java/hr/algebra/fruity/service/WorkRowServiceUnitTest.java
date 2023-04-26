package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateWorkRowRequestDtoWithWorkAdapter;
import hr.algebra.fruity.dto.response.FullWorkRowResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.WorkRowMapper;
import hr.algebra.fruity.model.WorkRow;
import hr.algebra.fruity.repository.WorkRowRepository;
import hr.algebra.fruity.service.impl.CurrentUserWorkRowService;
import hr.algebra.fruity.utils.mother.dto.CreateWorkRowRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullWorkRowResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateWorkRowRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.UserMother;
import hr.algebra.fruity.utils.mother.model.WorkRowMother;
import hr.algebra.fruity.utils.mother.model.WorkMother;
import hr.algebra.fruity.validator.CreateWorkRowRequestDtoWithWorkAdapterValidator;
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
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("WorkRow Service Unit Test")
@SuppressWarnings("static-access")
public class WorkRowServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserWorkRowService workRowService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private CreateWorkRowRequestDtoWithWorkAdapterValidator createWorkRowRequestDtoWithWorkAdapterValidator;

  @Mock
  private WorkRowMapper workRowMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private WorkRowRepository workRowRepository;

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

    @Test
    @DisplayName("GIVEN workId and CreateWorkRowRequestDto " +
      "... THEN WorkRowResponseDto")
    public void GIVEN_LongAndCreateWorkRowRequestDto_THEN_WorkRowResponseDto() {
      // GIVEN
      // ... workId
      val workId = 1L;
      // ... CreateWorkRowRequestDto
      val requestDto = CreateWorkRowRequestDtoMother.complete().build();
      // ... WorkService successfully gets Work by id
      val work = WorkMother.complete().build();
      given(workService.getById(same(workId))).willReturn(work);
      // ... CreateWorkRowRequestDtoWithWorkAdapterValidator successfully validates requestDto and Work
      willDoNothing().given(createWorkRowRequestDtoWithWorkAdapterValidator).validate(any(CreateWorkRowRequestDtoWithWorkAdapter.class));
      // ... ConversionService successfully converts from CreateWorkRowRequestDtoWithWorkAdapter to WorkRow
      val workRow = WorkRowMother.complete().build();
      given(conversionService.convert(any(CreateWorkRowRequestDtoWithWorkAdapter.class), same(WorkRow.class))).willReturn(workRow);
      // ... WorkRowRepository will successfully save WorkRow
      given(workRowRepository.save(same(workRow))).willReturn(workRow);
      // ... ConversionService successfully converts from WorkRow to FullWorkRowResponseDto
      val expectedResponseDto = FullWorkRowResponseDtoMother.complete().build();
      given(conversionService.convert(same(workRow), same(FullWorkRowResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... createWorkRowForWorkId is called
      val responseDto = workRowService.createWorkRowForWorkId(workId, requestDto);

      // THEN
      // ... WorkRowResponseDto
      then(workRowRepository).should(times(1)).save(same(workRow));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
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