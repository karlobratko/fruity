package hr.algebra.fruity.properties;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("hr.algebra.fruity.email")
public record EmailProperties(
  @NotBlank
  String sender
) {
}
