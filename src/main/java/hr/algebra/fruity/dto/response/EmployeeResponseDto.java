package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record EmployeeResponseDto(
  Long id,
  String firstName,
  String lastName,
  String email,
  String phoneNumber,
  BigDecimal costPerHour,
  String role
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String firstName = "firstName";

    public static final String lastName = "lastName";

    public static final String email = "email";

    public static final String phoneNumber = "phoneNumber";

    public static final String costPerHour = "costPerHour";

    public static final String role = "role";

  }

}
