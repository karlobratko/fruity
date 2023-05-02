package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.exception.WorkAlreadyFinishedException;
import hr.algebra.fruity.repository.RealisationHarvestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapterValidator implements Validator<JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapter> {

  private final RealisationHarvestRepository workHarvestRepository;

  @Override
  public void validate(JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapter target) {
    if (target.realisation().getWork().isFinished())
      throw new WorkAlreadyFinishedException();

    if (workHarvestRepository.existsByRealisationAndFruitCultivarAndHarvestedFruitClass(target.realisation(), target.dto().fruitCultivar(), target.dto().fruitClass()))
      throw new UniquenessViolatedException("Berba voća željene sorte i klase je već bila odrađena u realizaciji.");
  }

}
