package hr.algebra.fruity.service;

import hr.algebra.fruity.model.Employee;
import java.time.Instant;
import java.util.Map;
import java.util.function.Function;

public interface JwtTokenService {

  String getSubject(String token);

  Instant getExpiresAt(String token);

  String getUsername(String token);

  <T> T getClaim(String token, Function<Map<String, Object>, T> claimResolver);

  <T> T getClaim(String token, String claim);

  boolean isValid(String token, Employee employee);

  boolean isExpired(String token);

  String generate(Employee employee);

  String generate(Map<String, Object> claims);

}