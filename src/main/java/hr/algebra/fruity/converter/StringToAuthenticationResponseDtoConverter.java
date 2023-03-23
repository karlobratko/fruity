package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.AuthenticationResponseDto;
import hr.algebra.fruity.repository.EmployeeRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToAuthenticationResponseDtoConverter implements Converter<String, AuthenticationResponseDto> {

  @Override
  public AuthenticationResponseDto convert(@NonNull String source) {
    return new AuthenticationResponseDto(source);
  }

}
