package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullEmployeeResponseDto;
import hr.algebra.fruity.model.Employee;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmployeeToFullEmployeeResponseDtoConverter implements Converter<Employee, FullEmployeeResponseDto> {

  @Override
  public FullEmployeeResponseDto convert(@NonNull Employee source) {
    return new FullEmployeeResponseDto(
      source.getId(),
      source.getFirstName(),
      source.getLastName(),
      source.getUsername(),
      source.getEmail(),
      source.getPhoneNumber(),
      source.getCostPerHour(),
      source.getRole().getDisplayName(),
      source.getMobileToken().getUuid()
    );
  }

}
