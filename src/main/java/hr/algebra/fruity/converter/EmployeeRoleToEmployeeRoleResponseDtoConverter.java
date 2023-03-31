package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.EmployeeRoleResponseDto;
import hr.algebra.fruity.model.EmployeeRole;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmployeeRoleToEmployeeRoleResponseDtoConverter implements Converter<EmployeeRole, EmployeeRoleResponseDto> {

  @Override
  public EmployeeRoleResponseDto convert(@NonNull EmployeeRole source) {
    return new EmployeeRoleResponseDto(
      source.getId(),
      source.getDisplayName()
    );
  }

}
