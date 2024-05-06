package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.UpdateRealisationRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedUpdateRealisationRequestDto;
import hr.algebra.fruity.service.EmployeeService;
import hr.algebra.fruity.service.WorkService;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateRealisationRequestDtoToJoinedUpdateRealisationRequestDtoConverter implements Converter<UpdateRealisationRequestDto, JoinedUpdateRealisationRequestDto> {

  private final WorkService workService;

  private final EmployeeService employeeService;

  @Override
  public JoinedUpdateRealisationRequestDto convert(@NonNull UpdateRealisationRequestDto source) {
    return new JoinedUpdateRealisationRequestDto(
      Objects.nonNull(source.employeeFk()) ? employeeService.getById(source.employeeFk()) : null,
      source.startDateTime(),
      source.endDateTime(),
      source.note()
    );
  }

}
