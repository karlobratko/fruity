package hr.algebra.fruity.converter;

import hr.algebra.fruity.constants.JwtConstants;
import hr.algebra.fruity.dto.response.AuthenticationResponseDto;
import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.service.JwtTokenService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeToAuthenticationResponseDtoConverter implements Converter<Employee, AuthenticationResponseDto> {

  private final JwtTokenService jwtTokenService;

  @Override
  public AuthenticationResponseDto convert(@NonNull Employee source) {
    return new AuthenticationResponseDto(
      jwtTokenService.generate(source),
      source.getRefreshToken().getUuid(),
      JwtConstants.tokenType
    );
  }

}
