package hr.algebra.fruity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
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
@Table(name = RealisationHarvest.Constants.tableName)
@NoArgsConstructor(force = true)
@Builder
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class RealisationHarvest {

  @EmbeddedId
  @EqualsAndHashCode.Include
  @ToString.Include
  private RealisationHarvestId id;

  @MapsId(RealisationHarvestId.Fields.realisationFk)
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = Realisation.Constants.joinColumnName, nullable = false)
  private @NonNull Realisation realisation;

  @MapsId(RealisationHarvestId.Fields.fruitCultivarFk)
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = FruitCultivar.Constants.joinColumnName, nullable = false)
  private @NonNull FruitCultivar fruitCultivar;

  @MapsId(RealisationHarvestId.Fields.harvestedFruitClassFk)
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = HarvestedFruitClass.Constants.joinColumnName, nullable = false)
  private @NonNull HarvestedFruitClass harvestedFruitClass;

  @Column(name = Constants.quantityColumnName, precision = 18, scale = 9, nullable = false)
  private @NonNull BigDecimal quantity;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = UnitOfMeasure.Constants.joinColumnName, nullable = false)
  private @NonNull UnitOfMeasure unitOfMeasure;

  @Column(name = Constants.noteColumnName, length = 500)
  private String note;

  public RealisationHarvest(RealisationHarvestId id, @NonNull Realisation realisation, @NonNull FruitCultivar fruitCultivar, @NonNull HarvestedFruitClass harvestedFruitClass, @NonNull BigDecimal quantity, @NonNull UnitOfMeasure unitOfMeasure, String note) {
    this.id = Objects.requireNonNullElse(id, new RealisationHarvestId(realisation.getId(), fruitCultivar.getId(), harvestedFruitClass.getId()));
    this.realisation = realisation;
    this.fruitCultivar = fruitCultivar;
    this.harvestedFruitClass = harvestedFruitClass;
    this.quantity = quantity;
    this.unitOfMeasure = unitOfMeasure;
    this.note = note;
  }

  @Embeddable
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Getter
  @Setter
  @EqualsAndHashCode(doNotUseGetters = true)
  @ToString(doNotUseGetters = true)
  public static class RealisationHarvestId implements Serializable {

    @Column(name = Realisation.Constants.joinColumnName, nullable = false)
    private @NonNull Long realisationFk;

    @Column(name = FruitCultivar.Constants.joinColumnName, nullable = false)
    private @NonNull Integer fruitCultivarFk;

    @Column(name = HarvestedFruitClass.Constants.joinColumnName, nullable = false)
    private @NonNull Integer harvestedFruitClassFk;


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Fields {

      public static final String realisationFk = "realisationFk";

      public static final String fruitCultivarFk = "fruitCultivarFk";

      public static final String harvestedFruitClassFk = "harvestedFruitClassFk";

    }

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String realisation = "realisation";

    public static final String fruitCultivar = "fruitCultivar";

    public static final String harvestedFruitClass = "harvestedFruitClass";

    public static final String quantity = "quantity";

    public static final String unitOfMeasure = "unitOfMeasure";

    public static final String note = "note";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "realisations_harvests";

    public static final String joinColumnName = "realisation_harvest_fk";

    public static final String uuidColumnName = "uuid";

    public static final String quantityColumnName = "quantity";

    public static final String noteColumnName = "note";

  }

}
