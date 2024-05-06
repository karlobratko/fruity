package hr.algebra.fruity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(
  name = Attachment.Constants.tableName
//  uniqueConstraints = {
//    @UniqueConstraint(
//      name = Attachment.Constants.userAndNameUniqueConstraintName,
//      columnNames = {
//        User.Constants.joinColumnName,
//        Attachment.Constants.nameColumnName
//      }
//    )
//  }
)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE attachments SET delete_date = CURRENT_DATE WHERE attachment_id = ?", check = ResultCheckStyle.COUNT)
//@Where(clause = "delete_date IS NULL")
@FilterDef(name = "isNotDeleted", defaultCondition = "delete_date IS NULL")
@Filter(name = "isNotDeleted")
public class Attachment {

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

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = User.Constants.joinColumnName, nullable = false)
  private @NonNull User user;

  @Column(name = Constants.nameColumnName, nullable = false, length = 100)
  @ToString.Include
  private @NonNull String name;

  @Column(name = Constants.productionYearColumnName, nullable = false)
  private @NonNull Integer productionYear;

  @Column(name = Constants.costPerHourColumnName, precision = 15, scale = 6, nullable = false)
  private @NonNull BigDecimal costPerHour;

  @Column(name = Constants.purchasePriceColumnName, precision = 15, scale = 6, nullable = false)
  private @NonNull BigDecimal purchasePrice;

  @ManyToMany(mappedBy = Equipment.Fields.attachments, fetch = FetchType.LAZY)
  @Where(clause = "delete_date IS NULL")
  @Singular
  private Set<Equipment> equipments;

  @OneToMany(mappedBy = WorkAttachment.Fields.attachment, fetch = FetchType.LAZY)
  @Singular
  private Set<WorkAttachment> works;

  @OneToMany(mappedBy = RealisationAttachment.Fields.attachment, fetch = FetchType.LAZY)
  @Singular
  private Set<RealisationAttachment> realisations;

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

    public static final String user = "user";

    public static final String name = "name";

    public static final String productionYear = "productionYear";

    public static final String costPerHour = "costPerHour";

    public static final String purchasePrice = "purchasePrice";

    public static final String equipments = "equipments";

    public static final String works = "works";

    public static final String realisations = "realisations";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "attachments";

    public static final String joinColumnName = "attachment_fk";

    public static final String idColumnName = "attachment_id";

    public static final String uuidColumnName = "uuid";

    public static final String createDateColumnName = "create_date";

    public static final String updateDateColumnName = "update_date";

    public static final String deleteDateColumnName = "delete_date";

    public static final String nameColumnName = "name";

    public static final String productionYearColumnName = "production_year";

    public static final String costPerHourColumnName = "cost_per_hour";

    public static final String purchasePriceColumnName = "purchase_price";

    public static final String userAndNameUniqueConstraintName = "uq_attachment_user_name";

  }

}
