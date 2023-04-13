package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateCadastralParcelRequestDto;
import hr.algebra.fruity.dto.request.UpdateCadastralParcelRequestDto;
import hr.algebra.fruity.dto.response.CadastralParcelResponseDto;
import hr.algebra.fruity.dto.response.FullCadastralParcelResponseDto;
import hr.algebra.fruity.model.CadastralParcel;
import java.util.List;

public interface CadastralParcelService {

  List<CadastralParcelResponseDto> getAllCadastralParcels();

  FullCadastralParcelResponseDto getCadastralParcelById(Long id);

  FullCadastralParcelResponseDto createCadastralParcel(CreateCadastralParcelRequestDto requestDto);

  FullCadastralParcelResponseDto updateCadastralParcelById(Long id, UpdateCadastralParcelRequestDto requestDto);

  void deleteCadastralParcelById(Long id);

  CadastralParcel getById(Long id);

}
