package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.EmployeeResponseDto;
import hr.algebra.fruity.model.Employee;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmployeeToEmployeeResponseDtoConverter implements Converter<Employee, EmployeeResponseDto> {

  @Override
  public EmployeeResponseDto convert(@NonNull Employee source) {
    return new EmployeeResponseDto(
      source.getId(),
      source.getFirstName(),
      source.getLastName(),
      source.getUsername(),
      source.getEmail(),
      source.getCostPerHour(),
      source.getRole().getDisplayName()
    );
  }

}
