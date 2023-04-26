package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateWorkRowRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.WorkRow;
import hr.algebra.fruity.repository.RowRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkRowRequestDtoWithWorkAdapterToWorkRowConverter implements Converter<CreateWorkRowRequestDtoWithWorkAdapter, WorkRow> {

  private final RowRepository rowRepository;

  @Override
  public WorkRow convert(@NonNull CreateWorkRowRequestDtoWithWorkAdapter source) {
    return WorkRow.builder()
      .work(source.work())
      .row(rowRepository.findById(source.dto().rowFk()).orElseThrow(EntityNotFoundException::new))
      .note(source.dto().note())
      .build();
  }

}
