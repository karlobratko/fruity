package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateArcodeParcelRequestDto;
import hr.algebra.fruity.dto.request.UpdateArcodeParcelRequestDto;
import hr.algebra.fruity.dto.response.ArcodeParcelResponseDto;
import hr.algebra.fruity.dto.response.FullArcodeParcelResponseDto;
import hr.algebra.fruity.model.ArcodeParcel;
import java.util.List;

public interface ArcodeParcelService {

  List<ArcodeParcelResponseDto> getAllArcodeParcels();

  List<ArcodeParcelResponseDto> getAllArcodeParcelsByCadastralParcelId(Long cadastralParcelFk);

  FullArcodeParcelResponseDto getArcodeParcelById(Long id);

  FullArcodeParcelResponseDto createArcodeParcel(CreateArcodeParcelRequestDto requestDto);

  FullArcodeParcelResponseDto updateArcodeParcelById(Long id, UpdateArcodeParcelRequestDto requestDto);

  void deleteArcodeParcelById(Long id);

  ArcodeParcel getById(Long id);

}
