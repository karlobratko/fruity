package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.FullWorkResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.WorkMapper;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.repository.WorkRepository;
import hr.algebra.fruity.service.impl.CurrentUserWorkService;
import hr.algebra.fruity.utils.mother.dto.CreateWorkRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullWorkResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateWorkRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.UserMother;
import hr.algebra.fruity.utils.mother.model.WorkMother;
import hr.algebra.fruity.validator.CreateWorkRequestDtoValidator;
import hr.algebra.fruity.validator.WorkWithUpdateWorkRequestDtoValidator;
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
@DisplayName("WorkService Unit Test")
@SuppressWarnings("static-access")
public class WorkServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserWorkService workService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private CreateWorkRequestDtoValidator createWorkRequestDtoValidator;

  @Mock
  private WorkWithUpdateWorkRequestDtoValidator workWithUpdateWorkRequestDtoValidator;

  @Mock
  private WorkMapper workMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private WorkRepository workRepository;

  @Nested
  @DisplayName("WHEN getWorkById is called")
  public class WHEN_getWorkById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN WorkResponseDto is returned")
    public void GIVEN_id_THEN_WorkResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      val work = WorkMother.complete().build();
      given(workRepository.findById(same(id))).willReturn(Optional.of(work));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = work.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... ConversionService successfully converts from User to FullWorkResponseDto
      val expectedResponseDto = FullWorkResponseDtoMother.complete().build();
      given(conversionService.convert(same(work), same(FullWorkResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... getWorkById is called
      val responseDto = workService.getWorkById(id);

      // THEN
      // ... FullWorkResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createWork is called")
  public class WHEN_createWork {

    @Test
    @DisplayName("GIVEN CreateWorkRequestDto " +
      "... THEN WorkResponseDto")
    public void GIVEN_CreateWorkRequestDto_THEN_WorkResponseDto() {
      // GIVEN
      // ... CreateWorkRequestDto
      val requestDto = CreateWorkRequestDtoMother.complete().build();
      // CreateWorkRequestDtoValidator successfully validates CreateWorkRequestDto
      willDoNothing().given(createWorkRequestDtoValidator).validate(same(requestDto));
      // ... ConversionService successfully converts from CreateWorkRequestDto to Work
      val work = WorkMother.complete().build();
      given(conversionService.convert(same(requestDto), same(Work.class))).willReturn(work);
      // ... WorkRepository will successfully save Work
      given(workRepository.save(same(work))).willReturn(work);
      // ... ConversionService successfully converts from Work to FullWorkResponseDto
      val expectedResponseDto = FullWorkResponseDtoMother.complete().build();
      given(conversionService.convert(same(work), same(FullWorkResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... createWork is called
      val responseDto = workService.createWork(requestDto);

      // THEN
      // ... WorkResponseDto
      then(workRepository).should(times(1)).save(same(work));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateWorkById is called")
  public class WHEN_updateWorkById {

    @Test
    @DisplayName("GIVEN id and UpdateWorkRequestDto " +
      "... THEN WorkResponseDto is returned")
    public void GIVEN_idAndUpdateWorkRequestDto_THEN_WorkResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      val work = WorkMother.complete().build();
      given(workRepository.findById(same(id))).willReturn(Optional.of(work));
      // ... UpdateWorkRequestDto
      val requestDto = UpdateWorkRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = work.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... WorkWithUpdateWorkRequestDtoValidator successfully validates Work with UpdateWorkRequestDto
      willDoNothing().given(workWithUpdateWorkRequestDtoValidator).validate(same(work), same(requestDto));
      // ... WorkMapper successfully partially updates Work with UpdateWorkRequestDto
      given(workMapper.partialUpdate(same(work), same(requestDto))).willReturn(work);
      // ... WorkRepository successfully saves Work
      given(workRepository.save(same(work))).willReturn(work);
      // ... ConversionService successfully converts from Work to FullWorkResponseDto
      val expectedResponseDto = FullWorkResponseDtoMother.complete().build();
      given(conversionService.convert(same(work), same(FullWorkResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateWorkById is called
      val responseDto = workService.updateWorkById(id, requestDto);

      // THEN
      // ... WorkResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteWorkById is called")
  public class WHEN_deleteWorkById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN void")
    public void GIVEN_id_THEN_void() {
      // GIVEN
      // ... id
      val id = 1L;
      val work = WorkMother.complete().build();
      given(workRepository.findById(same(id))).willReturn(Optional.of(work));
      // ... CurrentUserService's logged-in User is not equal to Work User
      val loggedInUser = work.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... WorkRepository successfully deletes Work
      willDoNothing().given(workRepository).delete(work);

      // WHEN
      workService.deleteWorkById(id);

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
      given(workRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> workService.getById(id));

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
      val work = WorkMother.complete().build();
      given(workRepository.findById(same(id))).willReturn(Optional.of(work));
      // ... CurrentUserService's logged-in User is not equal to Work User
      val loggedInUser = UserMother.complete().id(work.getUser().getId() + 1).build();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getById is called
      when(() -> workService.getById(id));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN Work is returned")
    public void GIVEN_id_THEN_Work() {
      // GIVEN
      // ... id
      val id = 1L;
      val expectedWork = WorkMother.complete().build();
      given(workRepository.findById(same(id))).willReturn(Optional.of(expectedWork));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = expectedWork.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getById is called
      val returnedWork = workService.getById(id);

      // THEN
      // ... WorkResponseDto is returned
      and.then(returnedWork)
        .isNotNull()
        .isEqualTo(expectedWork);
    }

  }

}