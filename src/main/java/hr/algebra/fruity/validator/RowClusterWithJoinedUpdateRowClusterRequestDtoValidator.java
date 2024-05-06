package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedUpdateRowClusterRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.model.RowCluster;
import hr.algebra.fruity.repository.RowClusterRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RowClusterWithJoinedUpdateRowClusterRequestDtoValidator implements WithValidator<RowCluster, JoinedUpdateRowClusterRequestDto> {

  private final RowClusterRepository rowClusterRepository;

  @Override
  public void validate(RowCluster target, JoinedUpdateRowClusterRequestDto with) {
    if (Objects.nonNull(with.name()) || Objects.nonNull(with.arcodeParcel()))
      rowClusterRepository.findByNameAndArcodeParcel(
          Objects.requireNonNullElse(with.name(), target.getName()),
          Objects.requireNonNullElse(with.arcodeParcel(), target.getArcodeParcel())
        )
        .ifPresent(it -> {
          if (!Objects.equals(it, target))
            throw new UniquenessViolatedException("Naziv već postoji unutar ARKOD parcele i nije jedinstven.");
        });
  }

}