package hr.algebra.fruity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.LocalDate;
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
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(
  name = ArcodeParcel.Constants.tableName,
  uniqueConstraints = {
    @UniqueConstraint(
      name = ArcodeParcel.Constants.cadastralParcelAndNameUniqueConstrainName,
      columnNames = {
        CadastralParcel.Constants.joinColumnName,
        ArcodeParcel.Constants.nameColumnName
      }
    )
  }
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE arcode_parcels SET delete_date = CURRENT_DATE WHERE arcode_parcel_id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "delete_date IS NULL")
public class ArcodeParcel {

  @Id
  @Column(name = Constants.idColumnName)
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @EqualsAndHashCode.Include
  @ToString.Include
  private Long id;

  @Column(name = Constants.uuidColumnName, nullable = false, unique = true)
  @EqualsAndHashCode.Include
  private UUID uuid;

  @CreatedDate
  @Column(name = Constants.createDateColumnName, nullable = false)
  @Temporal(TemporalType.DATE)
  private LocalDate createDate;

  @LastModifiedDate
  @Column(name = Constants.updateDateColumnName, nullable = false)
  @Temporal(TemporalType.DATE)
  private LocalDate updateDate;

  @Column(name = Constants.deleteDateColumnName)
  @Temporal(TemporalType.DATE)
  private LocalDate deleteDate;

  @Column(name = Constants.nameColumnName, length = 100, nullable = false)
  @ToString.Include
  private @NonNull String name;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = CadastralParcel.Constants.joinColumnName, nullable = false)
  private @NonNull CadastralParcel cadastralParcel;

  @Column(name = Constants.arcodeColumnName, nullable = false, unique = true)
  @ToString.Include
  private @NonNull Integer arcode;

  @Column(name = Constants.surfaceColumnName, precision = 10, scale = 3, nullable = false)
  private @NonNull BigDecimal surface;

  @OneToMany(mappedBy = RowCluster.Fields.arcodeParcel, fetch = FetchType.LAZY)
  @Singular
  private Set<RowCluster> rowClusters;

  @PrePersist
  private void prePersist() {
    this.uuid = UUID.randomUUID();
  }

  @PreRemove
  private void preRemove() {
    this.deleteDate = LocalDate.now();
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String createDate = "createDate";

    public static final String updateDate = "updateDate";

    public static final String deleteDate = "deleteDate";

    public static final String cadastralParcel = "cadastralParcel";

    public static final String name = "name";

    public static final String arcode = "arcode";

    public static final String surface = "surface";

    public static final String rowClusters = "rowClusters";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "arcode_parcels";

    public static final String joinColumnName = "arcode_parcel_fk";

    public static final String idColumnName = "arcode_parcel_id";

    public static final String uuidColumnName = "uuid";

    public static final String createDateColumnName = "create_date";

    public static final String updateDateColumnName = "update_date";

    public static final String deleteDateColumnName = "delete_date";

    public static final String nameColumnName = "name";

    public static final String arcodeColumnName = "arcode";

    public static final String surfaceColumnName = "surface";

    public static final String cadastralParcelAndNameUniqueConstrainName = "uq_arcode_parcel_cadastral_parcel_name";

  }

}
