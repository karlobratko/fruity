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
@Table(name = User.Constants.tableName)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE users SET delete_date = CURRENT_DATE WHERE user_id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "delete_date IS NULL")
public class User {

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

  @Column(name = Constants.nameColumnName, length = 75)
  @ToString.Include
  private String name;

  @Column(name = Constants.oibColumnName, length = 11, nullable = false, unique = true)
  @ToString.Include
  private @NonNull String oib;

  @Column(name = Constants.phoneNumberColumnName, length = 25)
  private String phoneNumber;

  @Column(name = Constants.addressColumnName, length = 100)
  private String address;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = County.Constants.joinColumnName)
  private County county;

  @OneToMany(mappedBy = CadastralParcel.Fields.user, fetch = FetchType.LAZY)
  @Singular
  private Set<CadastralParcel> cadastralParcels;

  @OneToMany(mappedBy = Employee.Fields.user, fetch = FetchType.LAZY)
  @Singular
  private Set<Employee> employees;

  @OneToMany(mappedBy = Equipment.Fields.user, fetch = FetchType.LAZY)
  @Singular
  private Set<Equipment> equipments;

  @OneToMany(mappedBy = Attachment.Fields.user, fetch = FetchType.LAZY)
  @Singular
  private Set<Attachment> attachments;

  @OneToMany(mappedBy = Work.Fields.user, fetch = FetchType.LAZY)
  @Singular
  private Set<Work> works;

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

    public static final String name = "name";

    public static final String oib = "oib";

    public static final String phoneNumber = "phoneNumber";

    public static final String address = "address";

    public static final String county = "county";

    public static final String cadastralParcels = "cadastralParcels";

    public static final String employees = "employees";

    public static final String equipments = "equipments";

    public static final String attachments = "attachments";

    public static final String works = "works";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "users";

    public static final String joinColumnName = "user_fk";

    public static final String idColumnName = "user_id";

    public static final String uuidColumnName = "uuid";

    public static final String createDateColumnName = "create_date";

    public static final String updateDateColumnName = "update_date";

    public static final String deleteDateColumnName = "delete_date";

    public static final String nameColumnName = "name";

    public static final String oibColumnName = "oib";

    public static final String phoneNumberColumnName = "phone_number";

    public static final String addressColumnName = "address";

  }

}
