package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.AttachmentToAttachmentResponseDtoConverter;
import hr.algebra.fruity.converter.CreateAttachmentRequestDtoToAttachmentConverter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.AttachmentMapper;
import hr.algebra.fruity.repository.AttachmentRepository;
import hr.algebra.fruity.repository.RealisationAttachmentRepository;
import hr.algebra.fruity.repository.WorkAttachmentRepository;
import hr.algebra.fruity.service.impl.CurrentUserAttachmentService;
import hr.algebra.fruity.utils.mother.dto.AttachmentResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.CreateAttachmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UpdateAttachmentRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.AttachmentMother;
import hr.algebra.fruity.validator.AttachmentWithUpdateAttachmentRequestDtoValidator;
import hr.algebra.fruity.validator.CreateAttachmentRequestDtoValidator;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.val;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("AttachmentService Unit Test")
@SuppressWarnings("static-access")
public class AttachmentServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserAttachmentService attachmentService;

  @Mock
  private AttachmentToAttachmentResponseDtoConverter toAttachmentResponseDtoConverter;

  @Mock
  private CreateAttachmentRequestDtoToAttachmentConverter fromCreateAttachmentRequestDtoConverter;

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

  @Mock
  private WorkAttachmentRepository workAttachmentRepository;

  @Mock
  private RealisationAttachmentRepository realisationAttachmentRepository;

  @Mock
  private EntityManager entityManager;

  @BeforeEach
  public void beforeEach() {
    // ... EntityManager successfully returns session
    given(entityManager.unwrap(same(Session.class))).willReturn(mock(Session.class));
  }

  @Nested
  @DisplayName("WHEN getAttachmentById is called")
  public class WHEN_getAttachmentById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN AttachmentResponseDto is returned")
    public void GIVEN_id_THEN_AttachmentResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... ArcodeParcelRepository fails to findByIdAndUserId
      val attachment = AttachmentMother.complete().build();
      given(attachmentRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(attachment));
      // ... AttachmentToAttachmentResponseDtoConverter successfully converts
      val expectedResponseDto = AttachmentResponseDtoMother.complete().build();
      given(toAttachmentResponseDtoConverter.convert(same(attachment))).willReturn(expectedResponseDto);

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
      // CreateAttachmentRequestDtoValidator successfully validates
      willDoNothing().given(createAttachmentRequestDtoValidator).validate(same(requestDto));
      // ... CreateAttachmentRequestDtoToAttachmentConverter successfully converts
      val attachment = AttachmentMother.complete().build();
      given(fromCreateAttachmentRequestDtoConverter.convert(same(requestDto))).willReturn(attachment);
      // ... AttachmentRepository successfully saves
      given(attachmentRepository.save(same(attachment))).willReturn(attachment);
      // ... AttachmentToAttachmentResponseDtoConverter successfully converts
      val expectedResponseDto = AttachmentResponseDtoMother.complete().build();
      given(toAttachmentResponseDtoConverter.convert(same(attachment))).willReturn(expectedResponseDto);

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
    @DisplayName("GIVEN id and UpdateAttachmentRequestDto " +
      "... THEN AttachmentResponseDto is returned")
    public void GIVEN_idAndUpdateAttachmentRequestDto_THEN_AttachmentResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... UpdateAttachmentRequestDto
      val requestDto = UpdateAttachmentRequestDtoMother.complete().build();
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... ArcodeParcelRepository fails to findByIdAndUserId
      val attachment = AttachmentMother.complete().build();
      given(attachmentRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(attachment));
      // ... AttachmentWithUpdateAttachmentRequestDtoValidator successfully
      willDoNothing().given(attachmentWithUpdateAttachmentRequestDtoValidator).validate(same(attachment), same(requestDto));
      // ... AttachmentMapper successfully partially updates
      given(attachmentMapper.partialUpdate(same(attachment), same(requestDto))).willReturn(attachment);
      // ... AttachmentRepository successfully saves
      given(attachmentRepository.save(same(attachment))).willReturn(attachment);
      // ... AttachmentToAttachmentResponseDtoConverter successfully converts
      val expectedResponseDto = AttachmentResponseDtoMother.complete().build();
      given(toAttachmentResponseDtoConverter.convert(same(attachment))).willReturn(expectedResponseDto);

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
    @DisplayName("GIVEN id " +
      "... THEN void")
    public void GIVEN_id_THEN_void() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... ArcodeParcelRepository fails to findByIdAndUserId
      val attachment = AttachmentMother.complete().build();
      given(attachmentRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(attachment));
      // ... AttachmentRepository successfully deletes Attachment
      willDoNothing().given(attachmentRepository).delete(attachment);

      // WHEN
      attachmentService.deleteAttachmentById(id);

      // THEN
      // ... void
    }

  }

  @Nested
  @DisplayName("WHEN getById is called")
  public class WHEN_getById {

    @Test
    @DisplayName("GIVEN invalid id or userId " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidId_THEN_EntityNotFoundException() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... AttachmentRepository fails to findByIdAndUserId
      given(attachmentRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> attachmentService.getById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN Attachment is returned")
    public void GIVEN_id_THEN_Attachment() {
      // GIVEN
      // ... id
      val id = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... AttachmentRepository fails to findByIdAndUserId
      val expectedAttachment = AttachmentMother.complete().build();
      given(attachmentRepository.findByIdAndUserId(same(id), same(userId))).willReturn(Optional.of(expectedAttachment));

      // WHEN
      // ... getById is called
      val returnedAttachment = attachmentService.getById(id);

      // THEN
      // ... AttachmentResponseDto is returned
      and.then(returnedAttachment)
        .isNotNull()
        .isEqualTo(expectedAttachment);
    }

  }

}
