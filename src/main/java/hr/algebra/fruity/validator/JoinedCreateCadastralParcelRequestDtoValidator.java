package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedCreateCadastralParcelRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.CadastralParcelRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateCadastralParcelRequestDtoValidator implements Validator<JoinedCreateCadastralParcelRequestDto> {

  private final CadastralParcelRepository cadastralParcelRepository;

  private final CurrentRequestUserService currentRequestUserService;

  @Override
  public void validate(JoinedCreateCadastralParcelRequestDto target) {
    cadastralParcelRepository.findByNameAndUserId(target.name(), currentRequestUserService.getUserId())
      .ifPresent(ignored -> {
        throw new UniquenessViolatedException("Naziv već postoji i nije jedinstven.");
      });

    cadastralParcelRepository.findByCadastralMunicipalityAndCadastralNumber(target.cadastralMunicipality(), target.cadastralNumber())
      .ifPresent(ignored -> {
        throw new UniquenessViolatedException("Katastarski broj već postoji unutar katastarske općine i nije jedinstven.");
      });
  }

}