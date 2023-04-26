package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record CreateWorkAttachmentRequestDto(
  @NotNull(message = "Priključak je obavezno polje.")
  Long attachmentFk,
  @NotNull(message = "Cijena po satu je obavezno polje.")
  @DecimalMin(value = "0.00", message = "Cijena po satu mora biti pozitivan broj ili nula.")
  BigDecimal costPerHour,
  @Size(max = 500, message = "Napomena mora biti duljine maksimalno 500 znakova.")
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String attachmentFk = "attachmentFk";

    public static final String costPerHour = "costPerHour";

    public static final String note = "note";

  }

}
