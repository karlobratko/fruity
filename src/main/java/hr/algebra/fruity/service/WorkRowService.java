package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateWorkRowRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkRowRequestDto;
import hr.algebra.fruity.dto.response.FullWorkRowResponseDto;
import hr.algebra.fruity.dto.response.WorkRowResponseDto;
import hr.algebra.fruity.model.WorkRow;
import java.util.List;

public interface WorkRowService {

  List<WorkRowResponseDto> getAllWorkRowsByWorkId(Long workFk);

  FullWorkRowResponseDto getWorkRowByWorkIdAndRowId(Long workFk, Long rowFk);

  FullWorkRowResponseDto createWorkRowForWorkId(Long workFk, CreateWorkRowRequestDto requestDto);

  FullWorkRowResponseDto updateWorkRowByWorkIdAndRowId(Long workFk, Long rowFk, UpdateWorkRowRequestDto requestDto);

  void deleteWorkRowByWorkIdAndRowId(Long workFk, Long rowFk);

  WorkRow getByWorkIdAndRowId(Long workFk, Long rowFk);

}
