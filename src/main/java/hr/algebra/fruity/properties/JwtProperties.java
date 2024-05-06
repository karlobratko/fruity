package hr.algebra.fruity.properties;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "hr.algebra.fruity.security.jwt")
public record JwtProperties(
  @DurationUnit(ChronoUnit.MILLIS)
  Duration validityDurationInMs,
  @DurationUnit(ChronoUnit.MILLIS)
  Duration refreshValidityDurationInMs
) {
}