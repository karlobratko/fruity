package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkEmployeeRequestDtoWithWorkAdapter;
import hr.algebra.fruity.model.WorkEmployee;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JoinedCreateWorkEmployeeRequestDtoWithWorkAdapterToWorkEmployeeConverter implements Converter<JoinedCreateWorkEmployeeRequestDtoWithWorkAdapter, WorkEmployee> {

  @Override
  public WorkEmployee convert(@NonNull JoinedCreateWorkEmployeeRequestDtoWithWorkAdapter source) {
    return WorkEmployee.builder()
      .work(source.work())
      .employee(source.dto().employee())
      .costPerHour(source.dto().costPerHour())
      .note(source.dto().note())
      .build();
  }

}
