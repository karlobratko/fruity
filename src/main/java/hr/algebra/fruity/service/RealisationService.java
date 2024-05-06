package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateRealisationRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationRequestDto;
import hr.algebra.fruity.dto.response.FullRealisationResponseDto;
import hr.algebra.fruity.dto.response.RealisationResponseDto;
import hr.algebra.fruity.model.Realisation;
import java.util.List;

public interface RealisationService {

  List<RealisationResponseDto> getAllRealisations();

  FullRealisationResponseDto getRealisationById(Long id);

  FullRealisationResponseDto createRealisation(CreateRealisationRequestDto requestDto);

  FullRealisationResponseDto updateRealisationById(Long id, UpdateRealisationRequestDto requestDto);

  void deleteRealisationById(Long id);

  Realisation getById(Long id);

}
