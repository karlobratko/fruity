package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public record ResendRegistrationRequestDto(
  @NotNull(message = "Putanja do stranice za potvrdu registracije je nepoznata.")
  String confirmRegistrationUrl
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String confirmRegistrationUrl = "confirmRegistrationUrl";

  }

}
