package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateRealisationAttachmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationAttachmentRequestDto;
import hr.algebra.fruity.dto.response.FullRealisationAttachmentResponseDto;
import hr.algebra.fruity.dto.response.RealisationAttachmentResponseDto;
import hr.algebra.fruity.model.RealisationAttachment;
import java.util.List;

public interface RealisationAttachmentService {

  List<RealisationAttachmentResponseDto> getAllRealisationAttachmentsByRealisationId(Long realisationFk);

  FullRealisationAttachmentResponseDto getRealisationAttachmentByRealisationIdAndAttachmentId(Long realisationFk, Long attachmentFk);

  FullRealisationAttachmentResponseDto createRealisationAttachmentForRealisationId(Long realisationFk, CreateRealisationAttachmentRequestDto requestDto);

  FullRealisationAttachmentResponseDto updateRealisationAttachmentByRealisationIdAndAttachmentId(Long realisationFk, Long attachmentFk, UpdateRealisationAttachmentRequestDto requestDto);

  void deleteRealisationAttachmentByRealisationIdAndAttachmentId(Long realisationFk, Long attachmentFk);

  RealisationAttachment getByRealisationIdAndAttachmentId(Long realisationFk, Long attachmentFk);

}
