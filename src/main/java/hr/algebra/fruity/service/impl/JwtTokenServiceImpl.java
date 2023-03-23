package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.JwtDto;
import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.properties.JwtProperties;
import hr.algebra.fruity.service.JwtTokenService;
import hr.algebra.fruity.utils.ReflectionUtils;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

  private final ConversionService conversionService;

  private final JwtProperties jwtProperties;

  private final JwtEncoder jwtEncoder;

  private final JwtDecoder jwtDecoder;

  @Override
  public Instant getExpiresAt(@NonNull String token) {
    return jwtDecoder.decode(token).getExpiresAt();
  }

  @Override
  public String getSubject(@NonNull String token) {
    return jwtDecoder.decode(token).getSubject();
  }

  @Override
  public String getUsername(@NonNull String token) {
    return getSubject(token);
  }

  @Override
  public boolean isValid(@NonNull String token, @NonNull UserDetails userDetails) {
    return getSubject(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  @Override
  public String generate(@NonNull Employee employee) {
    return generate(
      employee.getUsername(),
      ReflectionUtils.objectToMap(
        Objects.requireNonNull(conversionService.convert(employee, JwtDto.class))
      )
    );
  }

  @Override
  public String generate(@NonNull String subject, @NonNull Map<String, Object> claims) {
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
