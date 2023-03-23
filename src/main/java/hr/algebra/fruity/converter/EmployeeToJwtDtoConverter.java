package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.JwtDto;
import hr.algebra.fruity.model.Employee;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class EmployeeToJwtDtoConverter implements Converter<Employee, JwtDto> {

  @Override
  public JwtDto convert(@NonNull Employee source) {
    return new JwtDto(
      source.getUser().getId(),
      source.getId(),
      source.getUsername(),
      source.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
    );
  }

}
