package hr.algebra.fruity.properties;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "hr.algebra.fruity.security.auth.registration")
public record RegistrationTokenProperties(
  @DurationUnit(ChronoUnit.MILLIS)
  Duration validityDurationInMs
) {

}
