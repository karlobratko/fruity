package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record ConfirmRegistrationRequestDto(
  @NotNull(message = "Registracijski token je obavezan.")
  UUID registrationToken
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String registrationToken = "registrationToken";

  }

}
