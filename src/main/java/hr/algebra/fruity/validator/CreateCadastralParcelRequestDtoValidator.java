package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.CreateCadastralParcelRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.CadastralParcelRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCadastralParcelRequestDtoValidator implements Validator<CreateCadastralParcelRequestDto> {

  private final CadastralParcelRepository cadastralParcelRepository;

  private final CurrentRequestUserService currentRequestUserService;

  @Override
  public void validate(CreateCadastralParcelRequestDto target) {
    cadastralParcelRepository.findByNameAndUserId(target.name(), currentRequestUserService.getUserId())
      .ifPresent(ignored -> {
        throw new UniquenessViolatedException("Naziv već postoji i nije jedinstven.");
      });

    cadastralParcelRepository.findByCadastralMunicipalityIdAndCadastralNumber(target.cadastralMunicipalityFk(), target.cadastralNumber())
      .ifPresent(ignored -> {
        throw new UniquenessViolatedException("Katastarski broj već postoji unutar katastarske općine i nije jedinstven.");
      });
  }

}