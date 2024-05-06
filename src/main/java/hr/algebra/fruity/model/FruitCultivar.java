package hr.algebra.fruity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Set;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

@Entity
@Table(
  name = FruitCultivar.Constants.tableName,
  uniqueConstraints = {
    @UniqueConstraint(
      name = FruitCultivar.Constants.nameAndFruitSpeciesUniqueConstraintName,
      columnNames = {
        FruitCultivar.Constants.nameColumnName,
        FruitSpecies.Constants.joinColumnName
      }
    )
  }
)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class FruitCultivar {

  @Id
  @Column(name = Constants.idColumnName)
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @EqualsAndHashCode.Include
  @ToString.Include
  private Integer id;

  @Column(name = Constants.uuidColumnName, nullable = false, unique = true)
  @EqualsAndHashCode.Include
  private UUID uuid;

  @Column(name = Constants.nameColumnName, length = 50, nullable = false)
  @ToString.Include
  private @NonNull String name;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = FruitSpecies.Constants.joinColumnName, nullable = false)
  private @NonNull FruitSpecies fruitSpecies;

  @OneToMany(mappedBy = Row.Fields.fruitCultivar, fetch = FetchType.LAZY)
  @Singular
  private Set<Row> rows;

  @OneToMany(mappedBy = RealisationHarvest.Fields.fruitCultivar, fetch = FetchType.LAZY)
  @Singular
  private Set<RealisationHarvest> realisations;

  @PrePersist
  private void prePersist() {
    this.uuid = UUID.randomUUID();
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String name = "name";

    public static final String fruitSpecies = "fruitSpecies";

    public static final String rows = "rows";

    public static final String realisations = "realisations";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "fruit_cultivars";

    public static final String joinColumnName = "fruit_cultivar_fk";

    public static final String idColumnName = "fruit_cultivar_id";

    public static final String uuidColumnName = "uuid";

    public static final String nameColumnName = "name";

    public static final String nameAndFruitSpeciesUniqueConstraintName = "uq_fruit_cultivars_name_fruit_species";

  }

}
