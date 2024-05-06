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
@Table(name = CadastralMunicipality.Constants.tableName)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class CadastralMunicipality {

  @Id
  @Column(name = Constants.idColumnName)
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @EqualsAndHashCode.Include
  @ToString.Include
  private Integer id;

  @Column(name = Constants.uuidColumnName, nullable = false, unique = true)
  @EqualsAndHashCode.Include
  private UUID uuid;

  @Column(name = Constants.registrationNumberColumnName, nullable = false, unique = true)
  @ToString.Include
  private @NonNull Integer registrationNumber;

  @Column(name = Constants.nameColumnName, length = 30, nullable = false)
  @ToString.Include
  private @NonNull String name;

  @Column(name = Constants.departmentColumnName, length = 30, nullable = true)
  @ToString.Include
  private String department;

  @Column(name = Constants.regionalCadastreOfficeColumnName, length = 30, nullable = false)
  @ToString.Include
  private @NonNull String regionalCadastreOffice;

  @OneToMany(mappedBy = CadastralParcel.Fields.cadastralMunicipality, fetch = FetchType.LAZY)
  @Singular
  private Set<CadastralParcel> cadastralParcels;

  @PrePersist
  private void prePersist() {
    this.uuid = UUID.randomUUID();
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String registrationNumber = "registrationNumber";

    public static final String name = "name";

    public static final String department = "department";

    public static final String regionalCadastreOffice = "regionalCadastreOffice";

    public static final String cadastralParcels = "cadastralParcels";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "cadastral_municipalities";

    public static final String joinColumnName = "cadastral_municipality_fk";

    public static final String idColumnName = "cadastral_municipality_id";

    public static final String uuidColumnName = "uuid";

    public static final String registrationNumberColumnName = "registration_number";

    public static final String nameColumnName = "name";

    public static final String departmentColumnName = "department";

    public static final String regionalCadastreOfficeColumnName = "regional_cadastre_office";

  }

}
