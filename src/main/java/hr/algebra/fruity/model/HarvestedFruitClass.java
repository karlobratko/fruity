package hr.algebra.fruity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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
@Table(name = HarvestedFruitClass.Constants.tableName)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class HarvestedFruitClass {

  @Id
  @Column(name = Constants.idColumnName)
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @EqualsAndHashCode.Include
  @ToString.Include
  private Integer id;

  @Column(name = Constants.uuidColumnName, nullable = false, unique = true)
  @EqualsAndHashCode.Include
  private UUID uuid;

  @Column(name = Constants.numberColumnName, nullable = false, unique = true)
  private @NonNull Integer number;

  @Column(name = Constants.nameColumnName, length = 10, nullable = false, unique = true)
  @ToString.Include
  private @NonNull String name;

  @Column(name = Constants.displayNameColumnName, length = 30, nullable = false, unique = true)
  private @NonNull String displayName;

  @OneToMany(mappedBy = RealisationHarvest.Fields.harvestedFruitClass, fetch = FetchType.LAZY)
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

    public static final String displayName = "displayName";

    public static final String name = "name";

    public static final String number = "number";

    public static final String realisations = "realisations";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "harvested_fruit_classes";

    public static final String joinColumnName = "harvested_fruit_class_fk";

    public static final String idColumnName = "harvested_fruit_class_id";

    public static final String uuidColumnName = "uuid";

    public static final String nameColumnName = "name";

    public static final String displayNameColumnName = "displayName";

    public static final String numberColumnName = "number";

  }

}
