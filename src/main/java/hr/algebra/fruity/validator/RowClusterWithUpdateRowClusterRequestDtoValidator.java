package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.UpdateRowClusterRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.model.RowCluster;
import hr.algebra.fruity.repository.RowClusterRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RowClusterWithUpdateRowClusterRequestDtoValidator implements WithValidator<RowCluster, UpdateRowClusterRequestDto> {

  private final RowClusterRepository arcodeParcelRepository;

  @Override
  public void validate(RowCluster target, UpdateRowClusterRequestDto with) {
    if (Objects.nonNull(with.name()) || Objects.nonNull(with.arcodeParcelFk()))
      arcodeParcelRepository.findByNameAndArcodeParcelId(
          Objects.requireNonNullElse(with.name(), target.getName()),
          Objects.requireNonNullElse(with.arcodeParcelFk(), target.getArcodeParcel().getId())
        )
        .ifPresent(it -> {
          if (!Objects.equals(it, target))
            throw new UniquenessViolatedException("Naziv već postoji unutar ARKOD parcele i nije jedinstven.");
        });
  }

}