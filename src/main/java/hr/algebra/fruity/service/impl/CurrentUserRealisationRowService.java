package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.CreateRealisationRowRequestDtoToJoinedCreateRealisationRowRequestDtoConverter;
import hr.algebra.fruity.converter.RealisationRowToFullRealisationRowResponseDtoConverter;
import hr.algebra.fruity.converter.RealisationRowToRealisationRowResponseDtoConverter;
import hr.algebra.fruity.dto.request.CreateRealisationRowRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationRowRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationRowRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.dto.response.FullRealisationRowResponseDto;
import hr.algebra.fruity.dto.response.RealisationRowResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.RealisationRowMapper;
import hr.algebra.fruity.model.Realisation;
import hr.algebra.fruity.model.RealisationRow;
import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.repository.RealisationRowRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.RealisationRowService;
import hr.algebra.fruity.service.RealisationService;
import hr.algebra.fruity.validator.JoinedCreateRealisationRowRequestDtoWithRealisationAdapterValidator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserRealisationRowService implements RealisationRowService {

  private final RealisationRowToRealisationRowResponseDtoConverter toRealisationRowResponseDtoConverter;

  private final RealisationRowToFullRealisationRowResponseDtoConverter toFullRealisationRowResponseDtoConverter;

  private final CreateRealisationRowRequestDtoToJoinedCreateRealisationRowRequestDtoConverter toJoinedCreateRealisationRowRequestDtoConverter;

  private final JoinedCreateRealisationRowRequestDtoWithRealisationAdapterValidator joinedCreateRealisationRowRequestDtoWithRealisationAdapterValidator;

  private final RealisationRowMapper realisationRowMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final RealisationRowRepository realisationRowRepository;

  private final RealisationService realisationService;

  @Override
  public List<RealisationRowResponseDto> getAllRealisationRowsByRealisationId(Long realisationFk) {
    return realisationRowRepository.findAllByRealisationOrderByRowRowClusterAscRowOrdinalAsc(realisationService.getById(realisationFk)).stream()
      .map(toRealisationRowResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullRealisationRowResponseDto getRealisationRowByRealisationIdAndRowId(Long realisationFk, Long rowFk) {
    return toFullRealisationRowResponseDtoConverter.convert(getByRealisationIdAndRowId(realisationFk, rowFk));
  }

  @Override
  public void createRealisationRowForRealisationId(Long realisationFk, CreateRealisationRowRequestDto requestDto) {
    val realisation = realisationService.getById(realisationFk);
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateRealisationRowRequestDtoConverter.convert(requestDto));

    removeExistingRowsInRealisation(joinedRequestDto.rows(), realisation);

    joinedCreateRealisationRowRequestDtoWithRealisationAdapterValidator.validate(new JoinedCreateRealisationRowRequestDtoWithRealisationAdapter(joinedRequestDto, realisation));

    realisationRowRepository.saveAll(
      joinedRequestDto.rows().stream()
        .map(row -> RealisationRow.builder().realisation(realisation).row(row).note(requestDto.note()).build())
        .toList()
    );
  }

  @Override
  public FullRealisationRowResponseDto updateRealisationRowByRealisationIdAndRowId(Long realisationFk, Long rowFk, UpdateRealisationRowRequestDto requestDto) {
    val realisationRow = getByRealisationIdAndRowId(realisationFk, rowFk);

    return toFullRealisationRowResponseDtoConverter.convert(
      realisationRowRepository.save(
        realisationRowMapper.partialUpdate(realisationRow, requestDto)
      )
    );
  }

  @Override
  public void deleteRealisationRowByRealisationIdAndRowId(Long realisationFk, Long rowFk) {
    realisationRowRepository.delete(getByRealisationIdAndRowId(realisationFk, rowFk));
  }

  @Override
  public RealisationRow getByRealisationIdAndRowId(Long realisationFk, Long rowFk) {
    return realisationRowRepository.findByRealisationIdAndRowIdAndRealisationWorkUserId(realisationFk, rowFk, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Red uključen u realizaciji"));
  }

  private void removeExistingRowsInRealisation(Set<Row> rows, Realisation realisation) {
    rows.removeAll(
      realisationRowRepository.findAllByRealisation(realisation).stream()
        .map(RealisationRow::getRow)
        .collect(Collectors.toUnmodifiableSet())
    );
  }

}
