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
@Table(name = Employee.Constants.tableName)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE Employee SET deleteDate = CURRENT_DATE WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = Employee.Constants.deleteDateColumnName + " IS NULL")
public class Employee {

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

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = User.Constants.joinColumnName, nullable = false)
  private @NonNull User user;

  @Column(name = Constants.firstNameColumnName, nullable = false, length = 50)
  @ToString.Include
  private @NonNull String firstName;

  @Column(name = Constants.lastNameColumnName, nullable = false, length = 50)
  @ToString.Include
  private @NonNull String lastName;

  @Column(name = Constants.emailColumnName, length = 254)
  private String email;

  @Column(name = Constants.phoneNumberColumnName, length = 25)
  private String phoneNumber;

  @Column(name = Constants.costPerHourColumnName, precision = 15, scale = 6, nullable = false)
  private @NonNull BigDecimal costPerHour;

  @Column(name = Constants.passwordColumnName, length = 512)
  private String password;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = EmployeeRole.Constants.joinColumnName, nullable = false)
  private @NonNull EmployeeRole role;

  @OneToMany(mappedBy = WorkEmployee.Fields.employee, fetch = FetchType.LAZY)
  @Singular
  private Set<WorkEmployee> works;

  @OneToMany(mappedBy = Realisation.Fields.employee, fetch = FetchType.LAZY)
  @Singular
  private Set<Realisation> realisations;

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

    public static final String firstName = "firstName";

    public static final String lastName = "lastName";

    public static final String phoneNumber = "phoneNumber";

    public static final String costPerHour = "costPerHour";

    public static final String email = "email";

    public static final String password = "password";

    public static final String role = "role";

    public static final String works = "works";

    public static final String realisations = "realisations";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "employees";

    public static final String joinColumnName = "employee_fk";

    public static final String idColumnName = "employee_id";

    public static final String uuidColumnName = "uuid";

    public static final String createDateColumnName = "create_date";

    public static final String updateDateColumnName = "update_date";

    public static final String deleteDateColumnName = "delete_date";

    public static final String firstNameColumnName = "first_name";

    public static final String lastNameColumnName = "last_name";

    public static final String phoneNumberColumnName = "phone_number";

    public static final String costPerHourColumnName = "cost_per_hour";

    public static final String emailColumnName = "email";

    public static final String passwordColumnName = "password";

  }

}
