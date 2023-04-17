package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateRowRequestDto;
import hr.algebra.fruity.dto.request.UpdateRowRequestDto;
import hr.algebra.fruity.dto.response.FullRowResponseDto;
import hr.algebra.fruity.dto.response.RowResponseDto;
import hr.algebra.fruity.model.Row;
import java.util.List;

public interface RowService {

  List<RowResponseDto> getAllRows();

  List<RowResponseDto> getAllRowsByRowClusterId(Long rowClusterFk);

  FullRowResponseDto getRowById(Long id);

  FullRowResponseDto createRow(CreateRowRequestDto requestDto);

  FullRowResponseDto updateRowById(Long id, UpdateRowRequestDto requestDto);

  void deleteRowById(Long id);

  Row getById(Long id);

}
