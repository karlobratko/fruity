package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.model.RealisationEquipment;
import hr.algebra.fruity.service.WorkEquipmentService;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterToRealisationEquipmentConverter implements Converter<JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapter, RealisationEquipment> {

  private final WorkEquipmentService workEquipmentService;

  @Override
  public RealisationEquipment convert(@NonNull JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapter source) {
    return RealisationEquipment.builder()
      .realisation(source.realisation())
      .equipment(source.dto().equipment())
      .costPerHour(Objects.requireNonNullElse(source.dto().costPerHour(), workEquipmentService.getByWorkIdAndEquipmentId(source.realisation().getWork().getId(), source.dto().equipment().getId()).getCostPerHour()))
      .note(source.dto().note())
      .build();
  }

}
