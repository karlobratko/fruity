package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateRowClusterRequestDto;
import hr.algebra.fruity.dto.request.UpdateRowClusterRequestDto;
import hr.algebra.fruity.dto.response.FullRowClusterResponseDto;
import hr.algebra.fruity.dto.response.RowClusterResponseDto;
import hr.algebra.fruity.model.RowCluster;
import java.util.List;

public interface RowClusterService {

  List<RowClusterResponseDto> getAllRowClusters();

  List<RowClusterResponseDto> getAllRowClustersByArcodeParcelId(Long arcodeParcelId);

  FullRowClusterResponseDto getRowClusterById(Long id);

  FullRowClusterResponseDto createRowCluster(CreateRowClusterRequestDto requestDto);

  FullRowClusterResponseDto updateRowClusterById(Long id, UpdateRowClusterRequestDto requestDto);

  void deleteRowClusterById(Long id);

  RowCluster getById(Long id);

}
