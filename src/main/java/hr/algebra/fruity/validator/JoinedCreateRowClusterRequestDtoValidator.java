package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRowClusterRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.RowClusterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateRowClusterRequestDtoValidator implements Validator<JoinedCreateRowClusterRequestDto> {

  private final RowClusterRepository rowClusterRepository;

  @Override
  public void validate(JoinedCreateRowClusterRequestDto target) {
    rowClusterRepository.findByNameAndArcodeParcel(target.name(), target.arcodeParcel())
      .ifPresent(ignored -> {
        throw new UniquenessViolatedException("Naziv već postoji unutar ARKOD parcele i nije jedinstven.");
      });
  }

}