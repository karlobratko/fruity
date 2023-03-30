package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record CreateEquipmentRequestDto(
  @NotNull(message = "Naziv je obavezno polje.")
  @Size(min = 3, max = 100, message = "Naziv mora biti duljine minimalno 3 i maksimalno 100 znakova.")
  String name,
  @NotNull(message = "Godina proizvodnje je obavezno polje.")
  @Min(value = 1900, message = "Godina proizvodnje mora biti broj veći od 1990.")
  Integer productionYear,
  @NotNull(message = "Cijena po satu je obavezno polje.")
  @DecimalMin(value = "0.00", message = "Cijena po satu mora biti pozitivan broj ili nula.")
  BigDecimal costPerHour,
  @NotNull(message = "Cijena kupnje je obavezno polje.")
  @DecimalMin(value = "0.00", message = "Cijena kupnje mora biti pozitivan broj ili nula.")
  BigDecimal purchasePrice,
  List<Long> compatibleAttachmentFks
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String name = "name";

    public static final String productionYear = "productionYear";

    public static final String costPerHour = "costPerHour";

    public static final String purchasePrice = "purchasePrice";

    public static final String compatibleAttachmentFks = "compatibleAttachmentFks";

  }

}
