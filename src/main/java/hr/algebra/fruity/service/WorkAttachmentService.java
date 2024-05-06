package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateWorkAttachmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkAttachmentRequestDto;
import hr.algebra.fruity.dto.response.FullWorkAttachmentResponseDto;
import hr.algebra.fruity.dto.response.WorkAttachmentResponseDto;
import hr.algebra.fruity.model.WorkAttachment;
import java.util.List;

public interface WorkAttachmentService {

  List<WorkAttachmentResponseDto> getAllWorkAttachmentsByWorkId(Long workId);

  FullWorkAttachmentResponseDto getWorkAttachmentByWorkIdAndAttachmentId(Long workFk, Long attachmentFk);

  FullWorkAttachmentResponseDto createWorkAttachmentForWorkId(Long workFk, CreateWorkAttachmentRequestDto requestDto);

  FullWorkAttachmentResponseDto updateWorkAttachmentByWorkIdAndAttachmentId(Long workFk, Long attachmentFk, UpdateWorkAttachmentRequestDto requestDto);

  void deleteWorkAttachmentByWorkIdAndAttachmentId(Long workFk, Long attachmentFk);

  WorkAttachment getByWorkIdAndAttachmentId(Long workFk, Long attachmentFk);

}
