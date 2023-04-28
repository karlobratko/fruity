package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.AttachmentToAttachmentResponseDtoConverter;
import hr.algebra.fruity.converter.CreateEquipmentRequestDtoToJoinedCreateEquipmentRequestDtoConverter;
import hr.algebra.fruity.converter.EquipmentToEquipmentResponseDtoConverter;
import hr.algebra.fruity.converter.EquipmentToFullEquipmentResponseDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateEquipmentRequestDtoToEquipmentConverter;
import hr.algebra.fruity.converter.UpdateEquipmentRequestDtoToJoinedUpdateEquipmentRequestDtoConverter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.EquipmentMapper;
import hr.algebra.fruity.repository.AttachmentRepository;
import hr.algebra.fruity.repository.EquipmentRepository;
import hr.algebra.fruity.service.impl.CurrentUserEquipmentService;
import hr.algebra.fruity.utils.mother.dto.CreateEquipmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullEquipmentResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedCreateEquipmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedUpdateEquipmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateEquipmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.EquipmentMother;
import hr.algebra.fruity.validator.EquipmentWithJoinedUpdateEquipmentRequestDtoValidator;
import hr.algebra.fruity.validator.JoinedCreateEquipmentRequestDtoValidator;
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
@DisplayName("EquipmentService Unit Test")
@SuppressWarnings("static-access")
public class EquipmentServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserEquipmentService equipmentService;

  @Mock
  private EquipmentToEquipmentResponseDtoConverter toEquipmentResponseDtoConverter;

  @Mock
  private EquipmentToFullEquipmentResponseDtoConverter toFullEquipmentResponseDtoConverter;

  @Mock
  private CreateEquipmentRequestDtoToJoinedCreateEquipmentRequestDtoConverter toJoinedCreateEquipmentRequestDtoConverter;

  @Mock
  private JoinedCreateEquipmentRequestDtoToEquipmentConverter fromJoinedCreateEquipmentRequestDtoConverter;

  @Mock
  private UpdateEquipmentRequestDtoToJoinedUpdateEquipmentRequestDtoConverter toJoinedUpdateEquipmentRequestDtoConverter;

  @Mock
  private AttachmentToAttachmentResponseDtoConverter toAttachmentResponseDtoConverter;

  @Mock
  private JoinedCreateEquipmentRequestDtoValidator joinedCreateEquipmentRequestDtoValidator;

  @Mock
  private EquipmentWithJoinedUpdateEquipmentRequestDtoValidator equipmentWithJoinedUpdateEquipmentRequestDtoValidator;

  @Mock
  private EquipmentMapper equipmentMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private EquipmentRepository equipmentRepository;

  @Mock
  private AttachmentRepository attachmentRepository;

  @Nested
  @DisplayName("WHEN getEquipmentById is called")
  public class WHEN_getEquipmentById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN EquipmentResponseDto is returned")
    public void GIVEN_id_THEN_EquipmentResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... EquipmentRepository successfully finds
      val equipment = EquipmentMother.complete().build();
      given(equipmentRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(equipment));
      // ... EquipmentToFullEquipmentResponseDtoConverter successfully converts
      val expectedResponseDto = FullEquipmentResponseDtoMother.complete().build();
      given(toFullEquipmentResponseDtoConverter.convert(same(equipment))).willReturn(expectedResponseDto);

      // WHEN
      // ... getEquipmentById is called
      val responseDto = equipmentService.getEquipmentById(id);

      // THEN
      // ... FullEquipmentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createEquipment is called")
  public class WHEN_createEquipment {

    @Test
    @DisplayName("GIVEN CreateEquipmentRequestDto " +
      "... THEN EquipmentResponseDto")
    public void GIVEN_CreateEquipmentRequestDto_THEN_EquipmentResponseDto() {
      // GIVEN
      // ... CreateEquipmentRequestDto
      val requestDto = CreateEquipmentRequestDtoMother.complete().build();
      // ... CreateEquipmentRequestDtoToJoinedCreateEquipmentRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedCreateEquipmentRequestDtoMother.complete().build();
      given(toJoinedCreateEquipmentRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... JoinedCreateEquipmentRequestDtoValidator successfully validates
      willDoNothing().given(joinedCreateEquipmentRequestDtoValidator).validate(same(joinedRequestDto));
      // ... JoinedCreateEquipmentRequestDtoToEquipmentConverter successfully converts
      val equipment = EquipmentMother.complete().build();
      given(fromJoinedCreateEquipmentRequestDtoConverter.convert(same(joinedRequestDto))).willReturn(equipment);
      // ... EquipmentRepository successfully saves
      given(equipmentRepository.save(same(equipment))).willReturn(equipment);
      // ... EquipmentToFullEquipmentResponseDtoConverter successfully converts
      val expectedResponseDto = FullEquipmentResponseDtoMother.complete().build();
      given(toFullEquipmentResponseDtoConverter.convert(same(equipment))).willReturn(expectedResponseDto);

      // WHEN
      // ... createEquipment is called
      val responseDto = equipmentService.createEquipment(requestDto);

      // THEN
      // ... EquipmentResponseDto
      then(equipmentRepository).should(times(1)).save(same(equipment));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateEquipmentById is called")
  public class WHEN_updateEquipmentById {

    @Test
    @DisplayName("GIVEN id and UpdateEquipmentRequestDto " +
      "... THEN EquipmentResponseDto is returned")
    public void GIVEN_idAndUpdateEquipmentRequestDto_THEN_EquipmentResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... UpdateEquipmentRequestDto
      val requestDto = UpdateEquipmentRequestDtoMother.complete().build();
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... EquipmentRepository successfully finds
      val equipment = EquipmentMother.complete().build();
      given(equipmentRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(equipment));
      // ... UpdateEquipmentRequestDtoToJoinedUpdateEquipmentRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedUpdateEquipmentRequestDtoMother.complete().build();
      given(toJoinedUpdateEquipmentRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... EquipmentWithJoinedUpdateEquipmentRequestDtoValidator successfully validates
      willDoNothing().given(equipmentWithJoinedUpdateEquipmentRequestDtoValidator).validate(same(equipment), same(joinedRequestDto));
      // ... EquipmentMapper successfully partially updates
      given(equipmentMapper.partialUpdate(same(equipment), same(joinedRequestDto))).willReturn(equipment);
      // ... EquipmentRepository successfully saves
      given(equipmentRepository.save(same(equipment))).willReturn(equipment);
      // ... EquipmentToFullEquipmentResponseDtoConverter successfully converts
      val expectedResponseDto = FullEquipmentResponseDtoMother.complete().build();
      given(toFullEquipmentResponseDtoConverter.convert(same(equipment))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateEquipmentById is called
      val responseDto = equipmentService.updateEquipmentById(id, requestDto);

      // THEN
      // ... EquipmentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteEquipmentById is called")
  public class WHEN_deleteEquipmentById {

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
      // ... EquipmentRepository successfully finds
      val equipment = EquipmentMother.complete().build();
      given(equipmentRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(equipment));
      // ... EquipmentRepository successfully deletes Equipment
      willDoNothing().given(equipmentRepository).delete(equipment);

      // WHEN
      equipmentService.deleteEquipmentById(id);

      // THEN
      // ... void
    }

  }

  @Nested
  @DisplayName("WHEN getById is called")
  public class WHEN_getById {

    @Test
    @DisplayName("GIVEN invalid id or userId" +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidIdOrUserId_THEN_EntityNotFoundException() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... EquipmentRepository fails to findByIdAndUserId
      given(equipmentRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> equipmentService.getById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN Equipment is returned")
    public void GIVEN_id_THEN_Equipment() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... EquipmentRepository successfully finds
      val expectedEquipment = EquipmentMother.complete().build();
      given(equipmentRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(expectedEquipment));

      // WHEN
      // ... getById is called
      val returnedEquipment = equipmentService.getById(id);

      // THEN
      // ... EquipmentResponseDto is returned
      and.then(returnedEquipment)
        .isNotNull()
        .isEqualTo(expectedEquipment);
    }

  }

}
