package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateRealisationRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationRequestDto;
import hr.algebra.fruity.service.EmployeeService;
import hr.algebra.fruity.service.WorkService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRealisationRequestDtoToJoinedCreateRealisationRequestDtoConverter implements Converter<CreateRealisationRequestDto, JoinedCreateRealisationRequestDto> {

  private final WorkService workService;

  private final EmployeeService employeeService;

  @Override
  public JoinedCreateRealisationRequestDto convert(@NonNull CreateRealisationRequestDto source) {
    return new JoinedCreateRealisationRequestDto(
      workService.getById(source.workFk()),
      employeeService.getById(source.employeeFk()),
      source.startDateTime(),
      source.endDateTime(),
      source.note()
    );
  }

}
