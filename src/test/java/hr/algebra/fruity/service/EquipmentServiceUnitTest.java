package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.FullEquipmentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.EquipmentMapper;
import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.repository.EquipmentRepository;
import hr.algebra.fruity.service.impl.CurrentUserEquipmentService;
import hr.algebra.fruity.utils.mother.dto.CreateEquipmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullEquipmentResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateEquipmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.EquipmentMother;
import hr.algebra.fruity.utils.mother.model.UserMother;
import hr.algebra.fruity.validator.CreateEquipmentRequestDtoValidator;
import hr.algebra.fruity.validator.EquipmentWithUpdateEquipmentRequestDtoValidator;
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
@DisplayName("EquipmentService Unit Test")
@SuppressWarnings("static-access")
public class EquipmentServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserEquipmentService equipmentService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private CreateEquipmentRequestDtoValidator createEquipmentRequestDtoValidator;

  @Mock
  private EquipmentWithUpdateEquipmentRequestDtoValidator equipmentWithUpdateEquipmentRequestDtoValidator;

  @Mock
  private EquipmentMapper equipmentMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private EquipmentRepository equipmentRepository;

  @Nested
  @DisplayName("WHEN getEquipmentById is called")
  public class WHEN_getEquipmentById {

    @Test
    @DisplayName("GIVEN invalid id " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidId_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid id
      val id = 1L;
      given(equipmentRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getEquipmentById is called
      when(() -> equipmentService.getEquipmentById(id));

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
      val equipment = EquipmentMother.complete().build();
      given(equipmentRepository.findById(same(id))).willReturn(Optional.of(equipment));
      // ... CurrentUserService's logged-in User is not equal to Equipment User
      val loggedInUser = UserMother.complete().id(equipment.getUser().getId() + 1).build();
      given(currentRequestUserService.getUser()).willReturn(loggedInUser);

      // WHEN
      // ... getEquipmentById is called
      when(() -> equipmentService.getEquipmentById(id));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN EquipmentResponseDto is returned")
    public void GIVEN_id_THEN_EquipmentResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      val equipment = EquipmentMother.complete().build();
      given(equipmentRepository.findById(same(id))).willReturn(Optional.of(equipment));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = equipment.getUser();
      given(currentRequestUserService.getUser()).willReturn(loggedInUser);
      // ... ConversionService successfully converts from User to FullEquipmentResponseDto
      val expectedResponseDto = FullEquipmentResponseDtoMother.complete().build();
      given(conversionService.convert(same(equipment), same(FullEquipmentResponseDto.class))).willReturn(expectedResponseDto);

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
      // CreateEquipmentRequestDtoValidator successfully validates CreateEquipmentRequestDto
      willDoNothing().given(createEquipmentRequestDtoValidator).validate(same(requestDto));
      // ... ConversionService successfully converts from CreateEquipmentRequestDto to Equipment
      val equipment = EquipmentMother.complete().build();
      given(conversionService.convert(same(requestDto), same(Equipment.class))).willReturn(equipment);
      // ... EquipmentRepository will successfully save Equipment
      given(equipmentRepository.save(same(equipment))).willReturn(equipment);
      // ... ConversionService successfully converts from Equipment to FullEquipmentResponseDto
      val expectedResponseDto = FullEquipmentResponseDtoMother.complete().build();
      given(conversionService.convert(same(equipment), same(FullEquipmentResponseDto.class))).willReturn(expectedResponseDto);

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
    @DisplayName("GIVEN invalid id and UpdateEquipmentRequestDto " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidIdAndUpdateEquipmentRequestDto_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid id
      val id = 1L;
      given(equipmentRepository.findById(same(id))).willReturn(Optional.empty());
      // ... UpdateEquipmentRequestDto
      val requestDto = UpdateEquipmentRequestDtoMother.complete().build();

      // WHEN
      // ... updateEquipmentById is called
      when(() -> equipmentService.updateEquipmentById(id, requestDto));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(EntityNotFoundException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id, UpdateEquipmentRequestDto, and foreign logged-in User " +
      "... THEN ForeignUserDataAccessException is thrown")
    public void GIVEN_idAndUpdateEquipmentRequestDtoAndForeignUser_THEN_ForeignUserDataAccessException() {
      // GIVEN
      // ... id
      val id = 1L;
      val equipment = EquipmentMother.complete().build();
      given(equipmentRepository.findById(same(id))).willReturn(Optional.of(equipment));
      // ... UpdateEquipmentRequestDto
      val requestDto = UpdateEquipmentRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is not equal to User
      val loggedInUser = UserMother.complete().id(equipment.getUser().getId() + 1).build();
      given(currentRequestUserService.getUser()).willReturn(loggedInUser);

      // WHEN
      // ... updateEquipmentById is called
      when(() -> equipmentService.updateEquipmentById(id, requestDto));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id and UpdateEquipmentRequestDto " +
      "... THEN EquipmentResponseDto is returned")
    public void GIVEN_idAndUpdateEquipmentRequestDto_THEN_EquipmentResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      val equipment = EquipmentMother.complete().build();
      given(equipmentRepository.findById(same(id))).willReturn(Optional.of(equipment));
      // ... UpdateEquipmentRequestDto
      val requestDto = UpdateEquipmentRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = equipment.getUser();
      given(currentRequestUserService.getUser()).willReturn(loggedInUser);
      // ... EquipmentWithUpdateEquipmentRequestDtoValidator successfully validates Equipment with UpdateEquipmentRequestDto
      willDoNothing().given(equipmentWithUpdateEquipmentRequestDtoValidator).validate(same(equipment), same(requestDto));
      // ... EquipmentMapper successfully partially updates Equipment with UpdateEquipmentRequestDto
      given(equipmentMapper.partialUpdate(same(equipment), same(requestDto))).willReturn(equipment);
      // ... EquipmentRepository successfully saves Equipment
      given(equipmentRepository.save(same(equipment))).willReturn(equipment);
      // ... ConversionService successfully converts from Equipment to FullEquipmentResponseDto
      val expectedResponseDto = FullEquipmentResponseDtoMother.complete().build();
      given(conversionService.convert(same(equipment), same(FullEquipmentResponseDto.class))).willReturn(expectedResponseDto);

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
    @DisplayName("GIVEN invalid id " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidId_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid id
      val id = 1L;
      given(equipmentRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getEquipmentById is called
      when(() -> equipmentService.deleteEquipmentById(id));

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
      val equipment = EquipmentMother.complete().build();
      given(equipmentRepository.findById(same(id))).willReturn(Optional.of(equipment));
      // ... CurrentUserService's logged-in User is not equal to Equipment User
      val loggedInUser = UserMother.complete().id(equipment.getUser().getId() + 1).build();
      given(currentRequestUserService.getUser()).willReturn(loggedInUser);

      // WHEN
      // ... getEquipmentById is called
      when(() -> equipmentService.deleteEquipmentById(id));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN void")
    public void GIVEN_id_THEN_void() {
      // GIVEN
      // ... id
      val id = 1L;
      val equipment = EquipmentMother.complete().build();
      given(equipmentRepository.findById(same(id))).willReturn(Optional.of(equipment));
      // ... CurrentUserService's logged-in User is not equal to Equipment User
      val loggedInUser = equipment.getUser();
      given(currentRequestUserService.getUser()).willReturn(loggedInUser);
      // ... EquipmentRepository successfully deletes Equipment
      willDoNothing().given(equipmentRepository).delete(equipment);

      // WHEN
      equipmentService.deleteEquipmentById(id);

      // THEN
      // ... void
    }

  }

}
