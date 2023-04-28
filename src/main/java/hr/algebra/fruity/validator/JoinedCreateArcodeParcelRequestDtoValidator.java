package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedCreateArcodeParcelRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.ArcodeParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateArcodeParcelRequestDtoValidator implements Validator<JoinedCreateArcodeParcelRequestDto> {

  private final ArcodeParcelRepository arcodeParcelRepository;

  @Override
  public void validate(JoinedCreateArcodeParcelRequestDto target) {
    arcodeParcelRepository.findByNameAndCadastralParcel(target.name(), target.cadastralParcel())
      .ifPresent(ignored -> {
        throw new UniquenessViolatedException("Naziv već postoji unutar katastarske čestice i nije jedinstven.");
      });

    arcodeParcelRepository.findByArcode(target.arcode())
      .ifPresent(ignored -> {
        throw new UniquenessViolatedException("ARKOD već postoji i nije jedinstven.");
      });
  }

}
