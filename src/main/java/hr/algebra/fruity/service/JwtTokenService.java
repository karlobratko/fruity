package hr.algebra.fruity.service;

import hr.algebra.fruity.model.Employee;
import java.time.Instant;
import java.util.Map;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {

  String getSubject(@NonNull String token);

  Instant getExpiresAt(@NonNull String token);

  String getUsername(@NonNull String token);

  boolean isValid(@NonNull String token, @NonNull UserDetails userDetails);

  String generate(@NonNull Employee employee);

  String generate(@NonNull String subject, @NonNull Map<String, Object> claims);

}