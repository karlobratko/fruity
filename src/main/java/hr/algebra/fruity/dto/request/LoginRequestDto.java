package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(
  @NotNull(message = "Korisničko ime je obavezno polje.")
  @Size(min = 5, max = 50, message = "Korisničko ime mora biti duljine minimalno 5 i maksimalno 50 znakova.")
  String username,
  @NotNull(message = "Lozinka je obavezno polje.")
  @Size(min = 8, max = 250, message = "Lozinka mora biti duljine minimalno 8 i  maksimalno 250 znakova.")
  String password
) {
}
