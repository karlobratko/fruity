package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.CreateWorkRequestDtoToJoinedCreateWorkRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateWorkRequestDtoToWorkConverter;
import hr.algebra.fruity.converter.RealisationToRealisationResponseDtoConverter;
import hr.algebra.fruity.converter.UpdateWorkRequestDtoToJoinedUpdateWorkRequestDtoConverter;
import hr.algebra.fruity.converter.WorkToFullWorkResponseDtoConverter;
import hr.algebra.fruity.converter.WorkToWorkResponseDtoConverter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.WorkMapper;
import hr.algebra.fruity.repository.RealisationRepository;
import hr.algebra.fruity.repository.WorkRepository;
import hr.algebra.fruity.service.impl.CurrentUserWorkService;
import hr.algebra.fruity.utils.mother.dto.CreateWorkRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullWorkResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedCreateWorkRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedUpdateWorkRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateWorkRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.WorkMother;
import hr.algebra.fruity.validator.JoinedCreateWorkRequestDtoValidator;
import hr.algebra.fruity.validator.WorkWithJoinedUpdateWorkRequestDtoValidator;
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
@DisplayName("Work Service Unit Test")
@SuppressWarnings("static-access")
public class WorkServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserWorkService workService;

  @Mock
  private WorkToWorkResponseDtoConverter toWorkResponseDtoConverter;

  @Mock
  private WorkToFullWorkResponseDtoConverter toFullWorkResponseDtoConverter;

  @Mock
  private CreateWorkRequestDtoToJoinedCreateWorkRequestDtoConverter toJoinedCreateWorkRequestDtoConverter;

  @Mock
  private JoinedCreateWorkRequestDtoToWorkConverter fromJoinedCreateWorkRequestDtoConverter;

  @Mock
  private UpdateWorkRequestDtoToJoinedUpdateWorkRequestDtoConverter toJoinedUpdateWorkRequestDtoConverter;

  @Mock
  private RealisationToRealisationResponseDtoConverter toRealisationResponseDtoConverter;

  @Mock
  private JoinedCreateWorkRequestDtoValidator joinedCreateWorkRequestDtoValidator;

  @Mock
  private WorkWithJoinedUpdateWorkRequestDtoValidator workWithJoinedUpdateWorkRequestDtoValidator;

  @Mock
  private WorkMapper workMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private WorkRepository workRepository;

  @Mock
  private RealisationRepository realisationRepository;

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
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkRepository fails to findByIdAndUserId
      val work = WorkMother.complete().build();
      given(workRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(work));
      // ... WorkToFullWorkResponseDtoConverter successfully converts
      val expectedResponseDto = FullWorkResponseDtoMother.complete().build();
      given(toFullWorkResponseDtoConverter.convert(same(work))).willReturn(expectedResponseDto);

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
      // ... CreateWorkRequestDtoToJoinedCreateWorkRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedCreateWorkRequestDtoMother.complete().build();
      given(toJoinedCreateWorkRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // CreateWorkRequestDtoValidator successfully validates
      willDoNothing().given(joinedCreateWorkRequestDtoValidator).validate(same(joinedRequestDto));
      // ... JoinedCreateWorkRequestDtoToWorkConverter successfully converts
      val work = WorkMother.complete().build();
      given(fromJoinedCreateWorkRequestDtoConverter.convert(same(joinedRequestDto))).willReturn(work);
      // ... WorkRepository successfully saves
      given(workRepository.save(same(work))).willReturn(work);
      // ... WorkToFullWorkResponseDtoConverter successfully converts
      val expectedResponseDto = FullWorkResponseDtoMother.complete().build();
      given(toFullWorkResponseDtoConverter.convert(same(work))).willReturn(expectedResponseDto);

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
      // ... UpdateWorkRequestDto
      val requestDto = UpdateWorkRequestDtoMother.complete().build();
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkRepository successfully finds
      val work = WorkMother.complete().build();
      given(workRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(work));
      // ... UpdateWorkRequestDtoToJoinedUpdateWorkRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedUpdateWorkRequestDtoMother.complete().build();
      given(toJoinedUpdateWorkRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... WorkWithUpdateWorkRequestDtoValidator successfully validates Work with UpdateWorkRequestDto
      willDoNothing().given(workWithJoinedUpdateWorkRequestDtoValidator).validate(same(work), same(joinedRequestDto));
      // ... WorkMapper successfully partially updates Work with UpdateWorkRequestDto
      given(workMapper.partialUpdate(same(work), same(joinedRequestDto))).willReturn(work);
      // ... WorkRepository successfully saves Work
      given(workRepository.save(same(work))).willReturn(work);
      // ... WorkToFullWorkResponseDtoConverter successfully converts
      val expectedResponseDto = FullWorkResponseDtoMother.complete().build();
      given(toFullWorkResponseDtoConverter.convert(same(work))).willReturn(expectedResponseDto);

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
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkRepository successfully finds
      val work = WorkMother.complete().build();
      given(workRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(work));
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
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkRepository fails to findByIdAndUserId
      given(workRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> workService.getById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN Work is returned")
    public void GIVEN_id_THEN_Work() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkRepository fails to findByIdAndUserId
      val expectedWork = WorkMother.complete().build();
      given(workRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(expectedWork));

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