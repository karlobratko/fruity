package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateRowRequestDto;
import hr.algebra.fruity.dto.request.UpdateRowRequestDto;
import hr.algebra.fruity.dto.response.FullRowResponseDto;
import hr.algebra.fruity.dto.response.RowResponseDto;
import hr.algebra.fruity.model.Row;
import java.util.List;

public interface RowService {

  List<RowResponseDto> getAllRows();

  FullRowResponseDto getRowById(Long id);

  FullRowResponseDto createRow(CreateRowRequestDto requestDto);

  FullRowResponseDto updateRowById(Long id, UpdateRowRequestDto requestDto);

  void deleteRowById(Long id);

  Row getById(Long id);

  Row getByIdIgnoreSoftDelete(Long id);

  List<Row> getAllById(List<Long> ids);

  List<Row> getAllByRowClusterId(Long rowClusterFk);

  List<Row> getAllByArcodeParcelId(Long arcodeParcelFk);

  List<Row> getAllByCadastralParcelId(Long cadastralParcelFk);

}
