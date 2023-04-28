package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkEquipmentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.model.WorkEquipment;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JoinedCreateWorkEquipmentRequestDtoWithWorkAdapterToWorkEquipmentConverter implements Converter<JoinedCreateWorkEquipmentRequestDtoWithWorkAdapter, WorkEquipment> {

  @Override
  public WorkEquipment convert(@NonNull JoinedCreateWorkEquipmentRequestDtoWithWorkAdapter source) {
    return WorkEquipment.builder()
      .work(source.work())
      .equipment(source.dto().equipment())
      .costPerHour(source.dto().costPerHour())
      .note(source.dto().note())
      .build();
  }

}
