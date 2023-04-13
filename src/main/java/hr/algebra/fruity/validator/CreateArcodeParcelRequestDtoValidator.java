package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.CreateArcodeParcelRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.ArcodeParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateArcodeParcelRequestDtoValidator implements Validator<CreateArcodeParcelRequestDto> {

  private final ArcodeParcelRepository arcodeParcelRepository;

  @Override
  public void validate(CreateArcodeParcelRequestDto target) {
    arcodeParcelRepository.findByNameAndCadastralParcelId(target.name(), target.cadastralParcelFk())
      .ifPresent(ignored -> {
        throw new UniquenessViolatedException("Naziv već postoji unutar katastarske čestice i nije jedinstven.");
      });

    arcodeParcelRepository.findByArcode(target.arcode())
      .ifPresent(ignored -> {
        throw new UniquenessViolatedException("ARKOD već postoji i nije jedinstven.");
      });
  }

}
