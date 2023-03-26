package hr.algebra.fruity.dto.request;

import hr.algebra.fruity.constraints.UniqueEmail;
import hr.algebra.fruity.constraints.UniqueOib;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public record RegisterRequestDto(
  @NotNull(message = "Ime je obavezno polje.")
  @Size(min = 2, max = 50, message = "Ime mora biti duljine minimalno 2 i maksimalno 50 znakova.")
  String firstName,
  @NotNull(message = "Prezime je obavezno polje.")
  @Size(min = 2, max = 50, message = "Prezime mora biti duljine minimalno 2 i maksimalno 50 znakova.")
  String lastName,
  @NotNull(message = "Korisničko ime je obavezno polje")
  @Size(min = 5, max = 50, message = "Korisničko ime mora biti duljine minimalno 5 i maksimalno 50 znakova.")
  String username,
  @NotNull(message = "Email je obavezno polje.")
  @Size(max = 254, message = "Email mora biti duljine maksimalno 254 znakova.")
  @Email(message = "Email mora biti važeći email.")
  @UniqueEmail
  String email,
  @NotNull(message = "OIB je obavezno polje.")
  @Size(min = 11, max = 11, message = "OIB mora biti duljine 11 znakova.")
  @UniqueOib
  String oib,
  @NotNull(message = "Lozinka je obavezno polje.")
  @Size(min = 8, message = "Lozinka mora biti duljine minimalno 8 i maksimalno 250 znakova.")
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,255}$", message = "Lozinka mora sadržavati minimalno jedan broj, jedno malo slovo, jedno veliko slovo, jedan specijalni znak i ne smije sadržavati razmake.")
  String password,
  @NotNull(message = "Putanja do stranice za potvrdu registracije je nepoznata.")
  String confirmRegistrationUrl
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String firstName = "firstName";

    public static final String lastName = "lastName";

    public static final String username = "username";

    public static final String email = "email";

    public static final String oib = "oib";

    public static final String password = "password";

    public static final String confirmRegistrationUrl = "confirmRegistrationUrl";

  }

}
