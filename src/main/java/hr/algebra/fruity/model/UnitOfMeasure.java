package hr.algebra.fruity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = UnitOfMeasure.Constants.tableName)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class UnitOfMeasure {

  @Id
  @Column(name = Constants.idColumnName)
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @EqualsAndHashCode.Include
  @ToString.Include
  private Integer id;

  @Column(name = Constants.uuidColumnName, nullable = false, unique = true)
  @EqualsAndHashCode.Include
  private UUID uuid;

  @Column(name = Constants.abbreviationColumnName, length = 10, nullable = false, unique = true)
  @ToString.Include
  private @NonNull String abbreviation;

  @Column(name = Constants.nameColumnName, length = 35, nullable = false, unique = true)
  private @NonNull String name;

  @ManyToOne(optional = true, fetch = FetchType.EAGER)
  @JoinColumn(name = Constants.baseUnitOfMeasureColumnName, nullable = true)
  private UnitOfMeasure base;

  @Column(name = Constants.baseMultiplierColumnName, precision = 18, scale = 9, nullable = false)
  @Builder.Default
  private @NonNull BigDecimal baseMultiplier = BigDecimal.ONE;

  @PrePersist
  private void prePersist() {
    this.uuid = UUID.randomUUID();
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String abbreviation = "abbreviation";

    public static final String name = "name";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "units_of_measure";

    public static final String joinColumnName = "unit_of_measure_fk";

    public static final String idColumnName = "unit_of_measure_id";

    public static final String uuidColumnName = "uuid";

    public static final String abbreviationColumnName = "abbreviation";

    public static final String nameColumnName = "name";

    public static final String baseUnitOfMeasureColumnName = "base_unit_of_measure_fk";

    public static final String baseMultiplierColumnName = "base_multiplier";

  }

}
