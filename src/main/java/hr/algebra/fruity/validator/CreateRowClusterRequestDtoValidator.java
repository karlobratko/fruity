package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.CreateRowClusterRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.RowClusterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRowClusterRequestDtoValidator implements Validator<CreateRowClusterRequestDto> {

  private final RowClusterRepository arcodeParcelRepository;

  @Override
  public void validate(CreateRowClusterRequestDto target) {
    arcodeParcelRepository.findByNameAndArcodeParcelId(target.name(), target.arcodeParcelFk())
      .ifPresent(ignored -> {
        throw new UniquenessViolatedException("Naziv već postoji unutar ARKOD parcele i nije jedinstven.");
      });
  }

}