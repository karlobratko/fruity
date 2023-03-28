package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.EmployeeToJwtDtoConverter;
import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.properties.JwtProperties;
import hr.algebra.fruity.service.JwtTokenService;
import hr.algebra.fruity.utils.ReflectionUtils;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

  private final EmployeeToJwtDtoConverter employeeToJwtDtoConverter;

  private final JwtProperties jwtProperties;

  private final JwtEncoder jwtEncoder;

  private final JwtDecoder jwtDecoder;

  @Override
  public Instant getExpiresAt(String token) {
    return jwtDecoder.decode(token).getExpiresAt();
  }

  @Override
  public String getSubject(String token) {
    return jwtDecoder.decode(token).getSubject();
  }

  @Override
  public String getUsername(String token) {
    return getSubject(token);
  }

  @Override
  public <T> T getClaim(String token, Function<Map<String, Object>, T> claimResolver) {
    return claimResolver.apply(jwtDecoder.decode(token).getClaims());
  }

  @Override
  public <T> T getClaim(String token, String claim) {
    return jwtDecoder.decode(token).getClaim(claim);
  }

  @Override
  public boolean isValid(String token, UserDetails userDetails) {
    return getSubject(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  @Override
  public String generate(Employee employee) {
    return generate(
      employee.getUsername(),
      ReflectionUtils.objectToMap(
        Objects.requireNonNull(employeeToJwtDtoConverter.convert(employee))
      )
    );
  }

  @Override
  public String generate(String subject, Map<String, Object> claims) {
    val now = Instant.now();
    val jwtClaimsSet = JwtClaimsSet.builder()
      .issuer("self")
      .issuedAt(now)
      .expiresAt(now.plus(jwtProperties.validityDurationInMs().toMillis(), ChronoUnit.MILLIS))
      .subject(subject)
      .claims(stringObjectMap -> stringObjectMap.putAll(claims))
      .build();
    return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
  }

  private boolean isTokenExpired(String token) {
    return getExpiresAt(token).isBefore(Instant.now());
  }

}
