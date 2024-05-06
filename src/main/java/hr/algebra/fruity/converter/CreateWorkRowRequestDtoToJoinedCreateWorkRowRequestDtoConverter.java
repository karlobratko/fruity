package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateWorkRowRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkRowRequestDto;
import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.service.RowService;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkRowRequestDtoToJoinedCreateWorkRowRequestDtoConverter implements Converter<CreateWorkRowRequestDto, JoinedCreateWorkRowRequestDto> {

  private final RowService rowService;

  @Override
  public JoinedCreateWorkRowRequestDto convert(@NonNull CreateWorkRowRequestDto source) {
    Set<Row> rows = new HashSet<>();

    if (Objects.nonNull(source.rowFks()))
      rows.addAll(rowService.getAllById(source.rowFks()));

    if (Objects.nonNull(source.rowClusterFk()))
      rows.addAll(rowService.getAllByRowClusterId(source.rowClusterFk()));

    if (Objects.nonNull(source.arcodeParcelFk()))
      rows.addAll(rowService.getAllByArcodeParcelId(source.arcodeParcelFk()));

    if (Objects.nonNull(source.cadastralParcelFk()))
      rows.addAll(rowService.getAllByCadastralParcelId(source.cadastralParcelFk()));

    return new JoinedCreateWorkRowRequestDto(
      rows,
      source.note()
    );
  }

}
