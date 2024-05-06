package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.CreateWorkAgentRequestDtoToJoinedCreateWorkAgentRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateWorkAgentRequestDtoWithWorkAdapterToWorkAgentConverter;
import hr.algebra.fruity.converter.WorkAgentToFullWorkAgentResponseDtoConverter;
import hr.algebra.fruity.converter.WorkAgentToWorkAgentResponseDtoConverter;
import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkAgentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.WorkAgentMapper;
import hr.algebra.fruity.repository.WorkAgentRepository;
import hr.algebra.fruity.service.impl.CurrentUserWorkAgentService;
import hr.algebra.fruity.utils.mother.dto.CreateWorkAgentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullWorkAgentResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedCreateWorkAgentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateWorkAgentRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.WorkAgentMother;
import hr.algebra.fruity.utils.mother.model.WorkMother;
import hr.algebra.fruity.validator.JoinedCreateWorkAgentRequestDtoWithWorkAdapterValidator;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("WorkAgent Service Unit Test")
@SuppressWarnings("static-access")
public class WorkAgentServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserWorkAgentService workAgentService;

  @Mock
  private WorkAgentToWorkAgentResponseDtoConverter toWorkAgentResponseDtoConverter;

  @Mock
  private WorkAgentToFullWorkAgentResponseDtoConverter toFullWorkAgentResponseDtoConverter;

  @Mock
  private CreateWorkAgentRequestDtoToJoinedCreateWorkAgentRequestDtoConverter toJoinedCreateWorkAgentRequestDtoConverter;

  @Mock
  private JoinedCreateWorkAgentRequestDtoWithWorkAdapterToWorkAgentConverter fromJoinedCreateWorkAgentRequestDtoWithWorkAdapterConverter;

  @Mock
  private JoinedCreateWorkAgentRequestDtoWithWorkAdapterValidator joinedCreateWorkAgentRequestDtoWithWorkAdapterValidator;

  @Mock
  private WorkAgentMapper workAgentMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private WorkAgentRepository workAgentRepository;

  @Mock
  private WorkService workService;

  @Nested
  @DisplayName("WHEN getWorkAgentByWorkIdAndAgentId is called")
  public class WHEN_getWorkAgentByWorkIdAndAgentId {

    @Test
    @DisplayName("GIVEN workId and agentId " +
      "... THEN WorkAgentResponseDto is returned")
    public void GIVEN_ids_THEN_WorkAgentResponseDto() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val agentId = 1;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkAgentRepository successfully finds
      val workAgent = WorkAgentMother.complete().build();
      given(workAgentRepository.findByWorkIdAndAgentIdAndWorkUserId(same(workId), same(agentId), same(userId))).willReturn(Optional.of(workAgent));
      // ... WorkAgentToFullWorkAgentResponseDtoConverter successfully converts
      val expectedResponseDto = FullWorkAgentResponseDtoMother.complete().build();
      given(toFullWorkAgentResponseDtoConverter.convert(same(workAgent))).willReturn(expectedResponseDto);

      // WHEN
      // ... getWorkAgentByWorkIdAndAgentId is called
      val responseDto = workAgentService.getWorkAgentByWorkIdAndAgentId(workId, agentId);

      // THEN
      // ... FullWorkAgentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createWorkAgentForWorkId is called")
  public class WHEN_createWorkAgentForWorkId {

    @Test
    @DisplayName("GIVEN workId and CreateWorkAgentRequestDto " +
      "... THEN WorkAgentResponseDto")
    public void GIVEN_LongAndCreateWorkAgentRequestDto_THEN_WorkAgentResponseDto() {
      // GIVEN
      // ... workId
      val workId = 1L;
      // ... CreateWorkAgentRequestDto
      val requestDto = CreateWorkAgentRequestDtoMother.complete().build();
      // ... WorkService successfully gets Work by id
      val work = WorkMother.complete().build();
      given(workService.getById(same(workId))).willReturn(work);
      // ... CreateWorkAgentRequestDtoToJoinedCreateWorkAgentRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedCreateWorkAgentRequestDtoMother.complete().build();
      given(toJoinedCreateWorkAgentRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... JoinedCreateWorkAgentRequestDtoWithWorkAdapterValidator successfully validates
      willDoNothing().given(joinedCreateWorkAgentRequestDtoWithWorkAdapterValidator).validate(any(JoinedCreateWorkAgentRequestDtoWithWorkAdapter.class));
      // ... JoinedCreateWorkAgentRequestDtoWithWorkAdapterToWorkAgentConverter successfully converts
      val workAgent = WorkAgentMother.complete().build();
      given(fromJoinedCreateWorkAgentRequestDtoWithWorkAdapterConverter.convert(any(JoinedCreateWorkAgentRequestDtoWithWorkAdapter.class))).willReturn(workAgent);
      // ... WorkAgentRepository successfully saves
      given(workAgentRepository.save(same(workAgent))).willReturn(workAgent);
      // ... WorkAgentToFullWorkAgentResponseDtoConverter successfully converts
      val expectedResponseDto = FullWorkAgentResponseDtoMother.complete().build();
      given(toFullWorkAgentResponseDtoConverter.convert(same(workAgent))).willReturn(expectedResponseDto);

      // WHEN
      // ... createWorkAgentForWorkId is called
      val responseDto = workAgentService.createWorkAgentForWorkId(workId, requestDto);

      // THEN
      // ... WorkAgentResponseDto
      then(workAgentRepository).should(times(1)).save(same(workAgent));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateWorkAgentByWorkIdAndAgentId is called")
  public class WHEN_updateWorkAgentByWorkIdAndAgentId {

    @Test
    @DisplayName("GIVEN workId, agentId, and UpdateWorkAgentRequestDto " +
      "... THEN WorkAgentResponseDto is returned")
    public void GIVEN_idsAndUpdateWorkAgentRequestDto_THEN_WorkAgentResponseDto() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val agentId = 1;
      // ... UpdateWorkAgentRequestDto
      val requestDto = UpdateWorkAgentRequestDtoMother.complete().build();
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkAgentRepository successfully finds
      val workAgent = WorkAgentMother.complete().build();
      given(workAgentRepository.findByWorkIdAndAgentIdAndWorkUserId(same(workId), same(agentId), same(userId))).willReturn(Optional.of(workAgent));
      // ... WorkAgentMapper successfully partially updates WorkAgent with UpdateWorkAgentRequestDto
      given(workAgentMapper.partialUpdate(same(workAgent), same(requestDto))).willReturn(workAgent);
      // ... WorkAgentRepository successfully saves WorkAgent
      given(workAgentRepository.save(same(workAgent))).willReturn(workAgent);
      // ... WorkAgentToFullWorkAgentResponseDtoConverter successfully converts
      val expectedResponseDto = FullWorkAgentResponseDtoMother.complete().build();
      given(toFullWorkAgentResponseDtoConverter.convert(same(workAgent))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateWorkAgentByWorkIdAndAgentId is called
      val responseDto = workAgentService.updateWorkAgentByWorkIdAndAgentId(workId, agentId, requestDto);

      // THEN
      // ... WorkAgentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteWorkAgentByWorkIdAndAgentId is called")
  public class WHEN_deleteWorkAgentByWorkIdAndAgentId {

    @Test
    @DisplayName("GIVEN workId and agentId " +
      "... THEN void")
    public void GIVEN_ids_THEN_void() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val agentId = 1;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkAgentRepository successfully finds
      val workAgent = WorkAgentMother.complete().build();
      given(workAgentRepository.findByWorkIdAndAgentIdAndWorkUserId(same(workId), same(agentId), same(userId))).willReturn(Optional.of(workAgent));
      // ... WorkAgentRepository successfully deletes WorkAgent
      willDoNothing().given(workAgentRepository).delete(workAgent);

      // WHEN
      // ... deleteWorkAgentByWorkIdAndAgentId is called
      workAgentService.deleteWorkAgentByWorkIdAndAgentId(workId, agentId);

      // THEN
      // ... void
    }

  }

  @Nested
  @DisplayName("WHEN getById is called")
  public class WHEN_getById {

    @Test
    @DisplayName("GIVEN invalid workId and agentId " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidIds_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val agentId = 1;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkAgentRepository fails to find
      given(workAgentRepository.findByWorkIdAndAgentIdAndWorkUserId(same(workId), same(agentId), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> workAgentService.getByWorkIdAndAgentId(workId, agentId));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN workId and agentId " +
      "... THEN WorkAgent is returned")
    public void GIVEN_ids_THEN_WorkAgent() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val agentId = 1;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkAgentRepository successfully finds
      val expectedWorkAgent = WorkAgentMother.complete().build();
      given(workAgentRepository.findByWorkIdAndAgentIdAndWorkUserId(same(workId), same(agentId), same(userId))).willReturn(Optional.of(expectedWorkAgent));

      // WHEN
      // ... getById is called
      val returnedWorkAgent = workAgentService.getByWorkIdAndAgentId(workId, agentId);

      // THEN
      // ... WorkAgentResponseDto is returned
      and.then(returnedWorkAgent)
        .isNotNull()
        .isEqualTo(expectedWorkAgent);
    }

  }

}