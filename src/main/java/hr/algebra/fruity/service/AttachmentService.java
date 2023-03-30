package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateAttachmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateAttachmentRequestDto;
import hr.algebra.fruity.dto.response.AttachmentResponseDto;
import java.util.List;

public interface AttachmentService {

  List<AttachmentResponseDto> getAllAttachments();

  AttachmentResponseDto getAttachmentById(Long id);

  AttachmentResponseDto createAttachment(CreateAttachmentRequestDto requestDto);

  AttachmentResponseDto updateAttachmentById(Long id, UpdateAttachmentRequestDto requestDto);

  void deleteAttachmentById(Long id);

}
