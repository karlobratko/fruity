package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateRealisationRowRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationRowRequestDto;
import hr.algebra.fruity.dto.response.FullRealisationRowResponseDto;
import hr.algebra.fruity.dto.response.RealisationRowResponseDto;
import hr.algebra.fruity.model.RealisationRow;
import java.util.List;

public interface RealisationRowService {

  List<RealisationRowResponseDto> getAllRealisationRowsByRealisationId(Long realisationFk);

  FullRealisationRowResponseDto getRealisationRowByRealisationIdAndRowId(Long realisationFk, Long rowFk);

  void createRealisationRowForRealisationId(Long realisationFk, CreateRealisationRowRequestDto requestDto);

  FullRealisationRowResponseDto updateRealisationRowByRealisationIdAndRowId(Long realisationFk, Long rowFk, UpdateRealisationRowRequestDto requestDto);


  void deleteRealisationRowByRealisationIdAndRowId(Long realisationFk, Long rowFk);

  RealisationRow getByRealisationIdAndRowId(Long realisationFk, Long rowFk);

}
