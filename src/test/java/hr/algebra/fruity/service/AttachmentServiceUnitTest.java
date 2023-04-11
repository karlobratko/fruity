package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.AttachmentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.AttachmentMapper;
import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.repository.AttachmentRepository;
import hr.algebra.fruity.service.impl.CurrentUserAttachmentService;
import hr.algebra.fruity.utils.mother.dto.CreateAttachmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.AttachmentResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateAttachmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.AttachmentMother;
import hr.algebra.fruity.utils.mother.model.UserMother;
import hr.algebra.fruity.validator.AttachmentWithUpdateAttachmentRequestDtoValidator;
import hr.algebra.fruity.validator.CreateAttachmentRequestDtoValidator;
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
@DisplayName("AttachmentService Unit Test")
@SuppressWarnings("static-access")
public class AttachmentServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserAttachmentService attachmentService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private CreateAttachmentRequestDtoValidator createAttachmentRequestDtoValidator;

  @Mock
  private AttachmentWithUpdateAttachmentRequestDtoValidator attachmentWithUpdateAttachmentRequestDtoValidator;

  @Mock
  private AttachmentMapper attachmentMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private AttachmentRepository attachmentRepository;

  @Nested
  @DisplayName("WHEN getAttachmentById is called")
  public class WHEN_getAttachmentById {

    @Test
    @DisplayName("GIVEN invalid id " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidId_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid id
      val id = 1L;
      given(attachmentRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getAttachmentById is called
      when(() -> attachmentService.getAttachmentById(id));

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
      val attachment = AttachmentMother.complete().build();
      given(attachmentRepository.findById(same(id))).willReturn(Optional.of(attachment));
      // ... CurrentUserService's logged-in User is not equal to Attachment User
      val loggedInUser = UserMother.complete().id(attachment.getUser().getId() + 1).build();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getAttachmentById is called
      when(() -> attachmentService.getAttachmentById(id));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN AttachmentResponseDto is returned")
    public void GIVEN_id_THEN_AttachmentResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      val attachment = AttachmentMother.complete().build();
      given(attachmentRepository.findById(same(id))).willReturn(Optional.of(attachment));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = attachment.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... ConversionService successfully converts from User to AttachmentResponseDto
      val expectedResponseDto = AttachmentResponseDtoMother.complete().build();
      given(conversionService.convert(same(attachment), same(AttachmentResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... getAttachmentById is called
      val responseDto = attachmentService.getAttachmentById(id);

      // THEN
      // ... AttachmentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createAttachment is called")
  public class WHEN_createAttachment {

    @Test
    @DisplayName("GIVEN CreateAttachmentRequestDto " +
      "... THEN AttachmentResponseDto")
    public void GIVEN_CreateAttachmentRequestDto_THEN_AttachmentResponseDto() {
      // GIVEN
      // ... CreateAttachmentRequestDto
      val requestDto = CreateAttachmentRequestDtoMother.complete().build();
      // CreateAttachmentRequestDtoValidator successfully validates CreateAttachmentRequestDto
      willDoNothing().given(createAttachmentRequestDtoValidator).validate(same(requestDto));
      // ... ConversionService successfully converts from CreateAttachmentRequestDto to Attachment
      val attachment = AttachmentMother.complete().build();
      given(conversionService.convert(same(requestDto), same(Attachment.class))).willReturn(attachment);
      // ... AttachmentRepository will successfully save Attachment
      given(attachmentRepository.save(same(attachment))).willReturn(attachment);
      // ... ConversionService successfully converts from Attachment to AttachmentResponseDto
      val expectedResponseDto = AttachmentResponseDtoMother.complete().build();
      given(conversionService.convert(same(attachment), same(AttachmentResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... createAttachment is called
      val responseDto = attachmentService.createAttachment(requestDto);

      // THEN
      // ... AttachmentResponseDto
      then(attachmentRepository).should(times(1)).save(same(attachment));
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateAttachmentById is called")
  public class WHEN_updateAttachmentById {

    @Test
    @DisplayName("GIVEN invalid id and UpdateAttachmentRequestDto " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidIdAndUpdateAttachmentRequestDto_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid id
      val id = 1L;
      given(attachmentRepository.findById(same(id))).willReturn(Optional.empty());
      // ... UpdateAttachmentRequestDto
      val requestDto = UpdateAttachmentRequestDtoMother.complete().build();

      // WHEN
      // ... updateAttachmentById is called
      when(() -> attachmentService.updateAttachmentById(id, requestDto));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(EntityNotFoundException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id, UpdateAttachmentRequestDto, and foreign logged-in User " +
      "... THEN ForeignUserDataAccessException is thrown")
    public void GIVEN_idAndUpdateAttachmentRequestDtoAndForeignUser_THEN_ForeignUserDataAccessException() {
      // GIVEN
      // ... id
      val id = 1L;
      val attachment = AttachmentMother.complete().build();
      given(attachmentRepository.findById(same(id))).willReturn(Optional.of(attachment));
      // ... UpdateAttachmentRequestDto
      val requestDto = UpdateAttachmentRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is not equal to User
      val loggedInUser = UserMother.complete().id(attachment.getUser().getId() + 1).build();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... updateAttachmentById is called
      when(() -> attachmentService.updateAttachmentById(id, requestDto));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id and UpdateAttachmentRequestDto " +
      "... THEN AttachmentResponseDto is returned")
    public void GIVEN_idAndUpdateAttachmentRequestDto_THEN_AttachmentResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      val attachment = AttachmentMother.complete().build();
      given(attachmentRepository.findById(same(id))).willReturn(Optional.of(attachment));
      // ... UpdateAttachmentRequestDto
      val requestDto = UpdateAttachmentRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = attachment.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... AttachmentWithUpdateAttachmentRequestDtoValidator successfully validates Attachment with UpdateAttachmentRequestDto
      willDoNothing().given(attachmentWithUpdateAttachmentRequestDtoValidator).validate(same(attachment), same(requestDto));
      // ... AttachmentMapper successfully partially updates Attachment with UpdateAttachmentRequestDto
      given(attachmentMapper.partialUpdate(same(attachment), same(requestDto))).willReturn(attachment);
      // ... AttachmentRepository successfully saves Attachment
      given(attachmentRepository.save(same(attachment))).willReturn(attachment);
      // ... ConversionService successfully converts from Attachment to AttachmentResponseDto
      val expectedResponseDto = AttachmentResponseDtoMother.complete().build();
      given(conversionService.convert(same(attachment), same(AttachmentResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateAttachmentById is called
      val responseDto = attachmentService.updateAttachmentById(id, requestDto);

      // THEN
      // ... AttachmentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteAttachmentById is called")
  public class WHEN_deleteAttachmentById {

    @Test
    @DisplayName("GIVEN invalid id " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidId_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid id
      val id = 1L;
      given(attachmentRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getAttachmentById is called
      when(() -> attachmentService.deleteAttachmentById(id));

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
      val attachment = AttachmentMother.complete().build();
      given(attachmentRepository.findById(same(id))).willReturn(Optional.of(attachment));
      // ... CurrentUserService's logged-in User is not equal to Attachment User
      val loggedInUser = UserMother.complete().id(attachment.getUser().getId() + 1).build();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());

      // WHEN
      // ... getAttachmentById is called
      when(() -> attachmentService.deleteAttachmentById(id));

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
      val attachment = AttachmentMother.complete().build();
      given(attachmentRepository.findById(same(id))).willReturn(Optional.of(attachment));
      // ... CurrentUserService's logged-in User is not equal to Attachment User
      val loggedInUser = attachment.getUser();
      given(currentRequestUserService.getUserId()).willReturn(loggedInUser.getId());
      // ... AttachmentRepository successfully deletes Attachment
      willDoNothing().given(attachmentRepository).delete(attachment);

      // WHEN
      attachmentService.deleteAttachmentById(id);

      // THEN
      // ... void
    }

  }

}
