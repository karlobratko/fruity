package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record LoginMobileRequestDto(
  @NotNull(message = "Token za pristup mobilnoj aplikaciji je obavezan.")
  UUID mobileToken
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String mobileToken = "mobileToken";

  }

}