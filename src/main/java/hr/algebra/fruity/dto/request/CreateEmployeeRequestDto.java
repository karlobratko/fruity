package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record CreateEmployeeRequestDto(
  @NotNull(message = "Ime je obavezno polje.")
  @Size(min = 2, max = 50, message = "Ime mora biti duljine minimalno 2 i maksimalno 50 znakova.")
  String firstName,
  @NotNull(message = "Prezime je obavezno polje.")
  @Size(min = 2, max = 50, message = "Prezime mora biti duljine minimalno 2 i maksimalno 50 znakova.")
  String lastName,
  @Size(max = 254, message = "Email mora biti duljine maksimalno 254 znakova.")
  @Email(message = "Email mora biti važeći email.")
  String email,
  @Size(max = 25, message = "Broj telefona mora biti duljine maksimalno 25 znakova.")
  String phoneNumber,
  @NotNull(message = "Cijena po satu je obavezno polje.")
  @DecimalMin(value = "0.00", message = "Cijena po satu mora biti pozitivan broj ili nula.")
  BigDecimal costPerHour
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String firstName = "firstName";

    public static final String lastName = "lastName";

    public static final String email = "email";

    public static final String phoneNumber = "phoneNumber";

    public static final String costPerHour = "costPerHour";

  }

}
