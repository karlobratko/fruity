package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record CreateRealisationAgentRequestDto(
  @NotNull(message = "Sredstvo je obavezno polje.")
  Integer agentFk,
  @NotNull(message = "Količina sredstva je obavezno polje.")
  @DecimalMin(value = "0.00", message = "Količina sredstva mora biti pozitivan broj ili nula.")
  BigDecimal agentQuantity,
  @NotNull(message = "Mjerna jedinica količine sredstva je obavezno polje.")
  Integer agentUnitOfMeasureFk,
  @NotNull(message = "Cijena po jedinici mjere je obavezno polje.")
  @DecimalMin(value = "0.00", message = "Cijena po jedinici mjere mora biti pozitivan broj ili nula.")
  BigDecimal costPerUnitOfMeasure,
  @NotNull(message = "Količina vode je obavezno polje.")
  @DecimalMin(value = "0.00", message = "Količina vode mora biti pozitivan broj ili nula.")
  BigDecimal waterQuantity,
  @NotNull(message = "Mjerna jedinica količine vode je obavezno polje.")
  Integer waterUnitOfMeasureFk,
  @Size(max = 500, message = "Napomena mora biti duljine maksimalno 500 znakova.")
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String agentFk = "agentFk";

    public static final String agentQuantity = "agentQuantity";

    public static final String agentUnitOfMeasureFk = "agentUnitOfMeasureFk";

    public static final String costPerUnitOfMeasure = "costPerUnitOfMeasure";

    public static final String waterQuantity = "waterQuantity";

    public static final String waterUnitOfMeasureFk = "waterUnitOfMeasureFk";

    public static final String note = "note";

  }

}
