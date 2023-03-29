package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record RefreshTokenRequestDto(
  @NotNull(message = "Token za osvježivanje je obavezan.")
  UUID refreshToken
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String refreshToken = "refreshToken";

  }

}
