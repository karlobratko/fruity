package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.CreateWorkEquipmentRequestDtoToJoinedCreateWorkEquipmentRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateWorkEquipmentRequestDtoWithWorkAdapterToWorkEquipmentConverter;
import hr.algebra.fruity.converter.WorkEquipmentToFullWorkEquipmentResponseDtoConverter;
import hr.algebra.fruity.converter.WorkEquipmentToWorkEquipmentResponseDtoConverter;
import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkEquipmentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.WorkEquipmentMapper;
import hr.algebra.fruity.repository.WorkEquipmentRepository;
import hr.algebra.fruity.service.impl.CurrentUserWorkEquipmentService;
import hr.algebra.fruity.utils.mother.dto.CreateWorkEquipmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullWorkEquipmentResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedCreateWorkEquipmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateWorkEquipmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.WorkEquipmentMother;
import hr.algebra.fruity.utils.mother.model.WorkMother;
import hr.algebra.fruity.validator.JoinedCreateWorkEquipmentRequestDtoWithWorkAdapterValidator;
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
@DisplayName("WorkEquipment Service Unit Test")
@SuppressWarnings("static-access")
public class WorkEquipmentServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserWorkEquipmentService workEquipmentService;

  @Mock
  private WorkEquipmentToWorkEquipmentResponseDtoConverter toWorkEquipmentResponseDtoConverter;

  @Mock
  private WorkEquipmentToFullWorkEquipmentResponseDtoConverter toFullWorkEquipmentResponseDtoConverter;

  @Mock
  private CreateWorkEquipmentRequestDtoToJoinedCreateWorkEquipmentRequestDtoConverter toJoinedCreateWorkEquipmentRequestDtoConverter;

  @Mock
  private JoinedCreateWorkEquipmentRequestDtoWithWorkAdapterToWorkEquipmentConverter fromJoinedCreateWorkEquipmentRequestDtoWithWorkAdapterConverter;

  @Mock
  private JoinedCreateWorkEquipmentRequestDtoWithWorkAdapterValidator joinedCreateWorkEquipmentRequestDtoWithWorkAdapterValidator;

  @Mock
  private WorkEquipmentMapper workEquipmentMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private WorkEquipmentRepository workEquipmentRepository;

  @Mock
  private WorkService workService;

  @Nested
  @DisplayName("WHEN getWorkEquipmentByWorkIdAndEquipmentId is called")
  public class WHEN_getWorkEquipmentByWorkIdAndEquipmentId {

    @Test
    @DisplayName("GIVEN workId and equipmentId " +
      "... THEN WorkEquipmentResponseDto is returned")
    public void GIVEN_ids_THEN_WorkEquipmentResponseDto() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val equipmentId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkEquipmentRepository successfully finds
      val workEquipment = WorkEquipmentMother.complete().build();
      given(workEquipmentRepository.findByWorkIdAndEquipmentIdAndWorkUserId(same(workId), same(equipmentId), same(userId))).willReturn(Optional.of(workEquipment));
      // ... WorkEquipmentToFullWorkEquipmentResponseDtoConverter successfully converts
      val expectedResponseDto = FullWorkEquipmentResponseDtoMother.complete().build();
      given(toFullWorkEquipmentResponseDtoConverter.convert(same(workEquipment))).willReturn(expectedResponseDto);

      // WHEN
      // ... getWorkEquipmentByWorkIdAndEquipmentId is called
      val responseDto = workEquipmentService.getWorkEquipmentByWorkIdAndEquipmentId(workId, equipmentId);

      // THEN
      // ... FullWorkEquipmentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createWorkEquipmentForWorkId is called")
  public class WHEN_createWorkEquipmentForWorkId {

    @Test
    @DisplayName("GIVEN workId and CreateWorkEquipmentRequestDto " +
      "... THEN WorkEquipmentResponseDto")
    public void GIVEN_LongAndCreateWorkEquipmentRequestDto_THEN_WorkEquipmentResponseDto() {
      // GIVEN
      // ... workId
      val workId = 1L;
      // ... CreateWorkEquipmentRequestDto
      val requestDto = CreateWorkEquipmentRequestDtoMother.complete().build();
      // ... WorkService successfully gets Work by id
      val work = WorkMother.complete().build();
      given(workService.getById(same(workId))).willReturn(work);
      // ... CreateWorkEquipmentRequestDtoToJoinedCreateWorkEquipmentRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedCreateWorkEquipmentRequestDtoMother.complete().build();
      given(toJoinedCreateWorkEquipmentRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... JoinedCreateWorkEquipmentRequestDtoWithWorkAdapterValidator successfully validates
      willDoNothing().given(joinedCreateWorkEquipmentRequestDtoWithWorkAdapterValidator).validate(any(JoinedCreateWorkEquipmentRequestDtoWithWorkAdapter.class));
      // ... JoinedCreateWorkEquipmentRequestDtoWithWorkAdapterToWorkEquipmentConverter successfully converts
      val workEquipment = WorkEquipmentMother.complete().build();
      given(fromJoinedCreateWorkEquipmentRequestDtoWithWorkAdapterConverter.convert(any(JoinedCreateWorkEquipmentRequestDtoWithWorkAdapter.class))).willReturn(workEquipment);
      // ... WorkEquipmentRepository successfully saves
      given(workEquipmentRepository.save(same(workEquipment))).willReturn(workEquipment);
      // ... WorkEquipmentToFullWorkEquipmentResponseDtoConverter successfully converts
      val expectedResponseDto = FullWorkEquipmentResponseDtoMother.complete().build();
      given(toFullWorkEquipmentResponseDtoConverter.convert(same(workEquipment))).willReturn(expectedResponseDto);

      // WHEN
      // ... createWorkEquipmentForWorkId is called
      val responseDto = workEquipmentService.createWorkEquipmentForWorkId(workId, requestDto);

      // THEN
      // ... WorkEquipmentResponseDto
      then(workEquipmentRepository).should(times(1)).save(same(workEquipment));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateWorkEquipmentByWorkIdAndEquipmentId is called")
  public class WHEN_updateWorkEquipmentByWorkIdAndEquipmentId {

    @Test
    @DisplayName("GIVEN workId, equipmentId, and UpdateWorkEquipmentRequestDto " +
      "... THEN WorkEquipmentResponseDto is returned")
    public void GIVEN_idsAndUpdateWorkEquipmentRequestDto_THEN_WorkEquipmentResponseDto() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val equipmentId = 1L;
      // ... UpdateWorkEquipmentRequestDto
      val requestDto = UpdateWorkEquipmentRequestDtoMother.complete().build();
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkEquipmentRepository successfully finds
      val workEquipment = WorkEquipmentMother.complete().build();
      given(workEquipmentRepository.findByWorkIdAndEquipmentIdAndWorkUserId(same(workId), same(equipmentId), same(userId))).willReturn(Optional.of(workEquipment));
      // ... WorkEquipmentMapper successfully partially updates WorkEquipment with UpdateWorkEquipmentRequestDto
      given(workEquipmentMapper.partialUpdate(same(workEquipment), same(requestDto))).willReturn(workEquipment);
      // ... WorkEquipmentRepository successfully saves WorkEquipment
      given(workEquipmentRepository.save(same(workEquipment))).willReturn(workEquipment);
      // ... WorkEquipmentToFullWorkEquipmentResponseDtoConverter successfully converts
      val expectedResponseDto = FullWorkEquipmentResponseDtoMother.complete().build();
      given(toFullWorkEquipmentResponseDtoConverter.convert(same(workEquipment))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateWorkEquipmentByWorkIdAndEquipmentId is called
      val responseDto = workEquipmentService.updateWorkEquipmentByWorkIdAndEquipmentId(workId, equipmentId, requestDto);

      // THEN
      // ... WorkEquipmentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteWorkEquipmentByWorkIdAndEquipmentId is called")
  public class WHEN_deleteWorkEquipmentByWorkIdAndEquipmentId {

    @Test
    @DisplayName("GIVEN workId and equipmentId " +
      "... THEN void")
    public void GIVEN_ids_THEN_void() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val equipmentId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkEquipmentRepository successfully finds
      val workEquipment = WorkEquipmentMother.complete().build();
      given(workEquipmentRepository.findByWorkIdAndEquipmentIdAndWorkUserId(same(workId), same(equipmentId), same(userId))).willReturn(Optional.of(workEquipment));
      // ... WorkEquipmentRepository successfully deletes WorkEquipment
      willDoNothing().given(workEquipmentRepository).delete(workEquipment);

      // WHEN
      // ... deleteWorkEquipmentByWorkIdAndEquipmentId is called
      workEquipmentService.deleteWorkEquipmentByWorkIdAndEquipmentId(workId, equipmentId);

      // THEN
      // ... void
    }

  }

  @Nested
  @DisplayName("WHEN getById is called")
  public class WHEN_getById {

    @Test
    @DisplayName("GIVEN invalid workId and equipmentId " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidIds_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val equipmentId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkEquipmentRepository fails to find
      given(workEquipmentRepository.findByWorkIdAndEquipmentIdAndWorkUserId(same(workId), same(equipmentId), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> workEquipmentService.getByWorkIdAndEquipmentId(workId, equipmentId));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN workId and equipmentId " +
      "... THEN WorkEquipment is returned")
    public void GIVEN_ids_THEN_WorkEquipment() {
      // GIVEN
      // ... invalid ids
      val workId = 1L;
      val equipmentId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... WorkEquipmentRepository successfully finds
      val expectedWorkEquipment = WorkEquipmentMother.complete().build();
      given(workEquipmentRepository.findByWorkIdAndEquipmentIdAndWorkUserId(same(workId), same(equipmentId), same(userId))).willReturn(Optional.of(expectedWorkEquipment));

      // WHEN
      // ... getById is called
      val returnedWorkEquipment = workEquipmentService.getByWorkIdAndEquipmentId(workId, equipmentId);

      // THEN
      // ... WorkEquipmentResponseDto is returned
      and.then(returnedWorkEquipment)
        .isNotNull()
        .isEqualTo(expectedWorkEquipment);
    }

  }

}