package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record UpdateUserRequestDto(
  @Size(min = 5, max = 75, message = "Naziv mora biti duljine minimalno 5 i maksimalno 75 znakova.")
  String name,
  @Size(min = 11, max = 11, message = "OIB mora biti duljine 11 znakova.")
  String oib,
  @Pattern(regexp = "^(\\+385)([ -]?)([1-9]\\d)([ -]?)(\\d{3})([ -]?)(\\d{4})$|^(\\+385)([ -]?)([1-9]\\d)([ -]?)(\\d{4})([ -]?)(\\d{3})$|^(\\+385)([ -]?)([1-9]\\d)([ -]?)(\\d{3})([ -]?)(\\d{3})$", message = "Broj telefona nije važeći.")
  String phoneNumber,
  @Size(max = 100, message = "Adresa mora biti duljine maksimalno 100 znakova.")
  String address,
  Integer countyFk
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String name = "name";

    public static final String oib = "oib";

    public static final String phoneNumber = "phoneNumber";

    public static final String address = "address";

    public static final String countyFk = "countyFk";

  }

}
