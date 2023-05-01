package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.CreateRealisationAgentRequestDtoToJoinedCreateRealisationAgentRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateRealisationAgentRequestDtoWithRealisationAdapterToRealisationAgentConverter;
import hr.algebra.fruity.converter.RealisationAgentToFullRealisationAgentResponseDtoConverter;
import hr.algebra.fruity.converter.RealisationAgentToRealisationAgentResponseDtoConverter;
import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationAgentRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.RealisationAgentMapper;
import hr.algebra.fruity.repository.RealisationAgentRepository;
import hr.algebra.fruity.service.impl.CurrentUserRealisationAgentService;
import hr.algebra.fruity.utils.mother.dto.CreateRealisationAgentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullRealisationAgentResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedCreateRealisationAgentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateRealisationAgentRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.RealisationAgentMother;
import hr.algebra.fruity.utils.mother.model.RealisationMother;
import hr.algebra.fruity.validator.JoinedCreateRealisationAgentRequestDtoWithRealisationAdapterValidator;
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
@DisplayName("RealisationAgent Service Unit Test")
@SuppressWarnings("static-access")
public class RealisationAgentServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserRealisationAgentService realisationAgentService;

  @Mock
  private RealisationAgentToRealisationAgentResponseDtoConverter toRealisationAgentResponseDtoConverter;

  @Mock
  private RealisationAgentToFullRealisationAgentResponseDtoConverter toFullRealisationAgentResponseDtoConverter;

  @Mock
  private CreateRealisationAgentRequestDtoToJoinedCreateRealisationAgentRequestDtoConverter toJoinedCreateRealisationAgentRequestDtoConverter;

  @Mock
  private JoinedCreateRealisationAgentRequestDtoWithRealisationAdapterToRealisationAgentConverter fromJoinedCreateRealisationAgentRequestDtoWithRealisationAdapterConverter;

  @Mock
  private JoinedCreateRealisationAgentRequestDtoWithRealisationAdapterValidator joinedCreateRealisationAgentRequestDtoWithRealisationAdapterValidator;

  @Mock
  private RealisationAgentMapper realisationAgentMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private RealisationAgentRepository realisationAgentRepository;

  @Mock
  private RealisationService workService;

  @Nested
  @DisplayName("WHEN getRealisationAgentByRealisationIdAndAgentId is called")
  public class WHEN_getRealisationAgentByRealisationIdAndAgentId {

    @Test
    @DisplayName("GIVEN workId and agentId " +
      "... THEN RealisationAgentResponseDto is returned")
    public void GIVEN_ids_THEN_RealisationAgentResponseDto() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val agentId = 1;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationAgentRepository successfully finds
      val realisationAgent = RealisationAgentMother.complete().build();
      given(realisationAgentRepository.findByRealisationIdAndAgentIdAndRealisationWorkUserId(same(workId), same(agentId), same(userId))).willReturn(Optional.of(realisationAgent));
      // ... RealisationAgentToFullRealisationAgentResponseDtoConverter successfully converts
      val expectedResponseDto = FullRealisationAgentResponseDtoMother.complete().build();
      given(toFullRealisationAgentResponseDtoConverter.convert(same(realisationAgent))).willReturn(expectedResponseDto);

      // WHEN
      // ... getRealisationAgentByRealisationIdAndAgentId is called
      val responseDto = realisationAgentService.getRealisationAgentByRealisationIdAndAgentId(workId, agentId);

      // THEN
      // ... FullRealisationAgentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createRealisationAgentForRealisationId is called")
  public class WHEN_createRealisationAgentForRealisationId {

    @Test
    @DisplayName("GIVEN workId and CreateRealisationAgentRequestDto " +
      "... THEN RealisationAgentResponseDto")
    public void GIVEN_LongAndCreateRealisationAgentRequestDto_THEN_RealisationAgentResponseDto() {
      // GIVEN
      // ... workId
      val workId = 1L;
      // ... CreateRealisationAgentRequestDto
      val requestDto = CreateRealisationAgentRequestDtoMother.complete().build();
      // ... RealisationService successfully gets Realisation by id
      val work = RealisationMother.complete().build();
      given(workService.getById(same(workId))).willReturn(work);
      // ... CreateRealisationAgentRequestDtoToJoinedCreateRealisationAgentRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedCreateRealisationAgentRequestDtoMother.complete().build();
      given(toJoinedCreateRealisationAgentRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... JoinedCreateRealisationAgentRequestDtoWithRealisationAdapterValidator successfully validates
      willDoNothing().given(joinedCreateRealisationAgentRequestDtoWithRealisationAdapterValidator).validate(any(JoinedCreateRealisationAgentRequestDtoWithRealisationAdapter.class));
      // ... JoinedCreateRealisationAgentRequestDtoWithRealisationAdapterToRealisationAgentConverter successfully converts
      val realisationAgent = RealisationAgentMother.complete().build();
      given(fromJoinedCreateRealisationAgentRequestDtoWithRealisationAdapterConverter.convert(any(JoinedCreateRealisationAgentRequestDtoWithRealisationAdapter.class))).willReturn(realisationAgent);
      // ... RealisationAgentRepository successfully saves
      given(realisationAgentRepository.save(same(realisationAgent))).willReturn(realisationAgent);
      // ... RealisationAgentToFullRealisationAgentResponseDtoConverter successfully converts
      val expectedResponseDto = FullRealisationAgentResponseDtoMother.complete().build();
      given(toFullRealisationAgentResponseDtoConverter.convert(same(realisationAgent))).willReturn(expectedResponseDto);

      // WHEN
      // ... createRealisationAgentForRealisationId is called
      val responseDto = realisationAgentService.createRealisationAgentForRealisationId(workId, requestDto);

      // THEN
      // ... RealisationAgentResponseDto
      then(realisationAgentRepository).should(times(1)).save(same(realisationAgent));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateRealisationAgentByRealisationIdAndAgentId is called")
  public class WHEN_updateRealisationAgentByRealisationIdAndAgentId {

    @Test
    @DisplayName("GIVEN workId, agentId, and UpdateRealisationAgentRequestDto " +
      "... THEN RealisationAgentResponseDto is returned")
    public void GIVEN_idsAndUpdateRealisationAgentRequestDto_THEN_RealisationAgentResponseDto() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val agentId = 1;
      // ... UpdateRealisationAgentRequestDto
      val requestDto = UpdateRealisationAgentRequestDtoMother.complete().build();
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationAgentRepository successfully finds
      val realisationAgent = RealisationAgentMother.complete().build();
      given(realisationAgentRepository.findByRealisationIdAndAgentIdAndRealisationWorkUserId(same(workId), same(agentId), same(userId))).willReturn(Optional.of(realisationAgent));
      // ... RealisationAgentMapper successfully partially updates RealisationAgent with UpdateRealisationAgentRequestDto
      given(realisationAgentMapper.partialUpdate(same(realisationAgent), same(requestDto))).willReturn(realisationAgent);
      // ... RealisationAgentRepository successfully saves RealisationAgent
      given(realisationAgentRepository.save(same(realisationAgent))).willReturn(realisationAgent);
      // ... RealisationAgentToFullRealisationAgentResponseDtoConverter successfully converts
      val expectedResponseDto = FullRealisationAgentResponseDtoMother.complete().build();
      given(toFullRealisationAgentResponseDtoConverter.convert(same(realisationAgent))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateRealisationAgentByRealisationIdAndAgentId is called
      val responseDto = realisationAgentService.updateRealisationAgentByRealisationIdAndAgentId(workId, agentId, requestDto);

      // THEN
      // ... RealisationAgentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteRealisationAgentByRealisationIdAndAgentId is called")
  public class WHEN_deleteRealisationAgentByRealisationIdAndAgentId {

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
      // ... RealisationAgentRepository successfully finds
      val realisationAgent = RealisationAgentMother.complete().build();
      given(realisationAgentRepository.findByRealisationIdAndAgentIdAndRealisationWorkUserId(same(workId), same(agentId), same(userId))).willReturn(Optional.of(realisationAgent));
      // ... RealisationAgentRepository successfully deletes RealisationAgent
      willDoNothing().given(realisationAgentRepository).delete(realisationAgent);

      // WHEN
      // ... deleteRealisationAgentByRealisationIdAndAgentId is called
      realisationAgentService.deleteRealisationAgentByRealisationIdAndAgentId(workId, agentId);

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
      // ... RealisationAgentRepository fails to find
      given(realisationAgentRepository.findByRealisationIdAndAgentIdAndRealisationWorkUserId(same(workId), same(agentId), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> realisationAgentService.getByRealisationIdAndAgentId(workId, agentId));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN workId and agentId " +
      "... THEN RealisationAgent is returned")
    public void GIVEN_ids_THEN_RealisationAgent() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val agentId = 1;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationAgentRepository successfully finds
      val expectedRealisationAgent = RealisationAgentMother.complete().build();
      given(realisationAgentRepository.findByRealisationIdAndAgentIdAndRealisationWorkUserId(same(workId), same(agentId), same(userId))).willReturn(Optional.of(expectedRealisationAgent));

      // WHEN
      // ... getById is called
      val returnedRealisationAgent = realisationAgentService.getByRealisationIdAndAgentId(workId, agentId);

      // THEN
      // ... RealisationAgentResponseDto is returned
      and.then(returnedRealisationAgent)
        .isNotNull()
        .isEqualTo(expectedRealisationAgent);
    }

  }

}