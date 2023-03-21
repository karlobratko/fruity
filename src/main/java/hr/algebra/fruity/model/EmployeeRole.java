package hr.algebra.fruity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = EmployeeRole.Constants.tableName)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class EmployeeRole {

  @Id
  @Column(name = Constants.idColumnName)
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @EqualsAndHashCode.Include
  @ToString.Include
  private Integer id;

  @Column(name = Constants.uuidColumnName, nullable = false, unique = true)
  @EqualsAndHashCode.Include
  private UUID uuid;

  @Column(name = Constants.nameColumnName, length = 50, nullable = false, unique = true)
  @ToString.Include
  private @NonNull String name;

  @Column(name = Constants.displayNameColumnName, length = 50, nullable = false, unique = true)
  private @NonNull String displayName;

  @OneToMany(mappedBy = Employee.Fields.role, fetch = FetchType.LAZY)
  @Singular
  private Set<Employee> employees;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = Constants.employeeRolePrivilegeJoinTableName,
    joinColumns = @JoinColumn(name = Constants.joinColumnName),
    inverseJoinColumns = @JoinColumn(name = EmployeePrivilege.Constants.joinColumnName)
  )
  @Singular
  private Set<EmployeePrivilege> privileges;

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

    public static final String employees = "employees";

    public static final String privileges = "privileges";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "employee_roles";

    public static final String employeeRolePrivilegeJoinTableName = "employee_roles_employee_privileges";

    public static final String joinColumnName = "employee_role_fk";

    public static final String idColumnName = "employee_role_id";

    public static final String uuidColumnName = "uuid";

    public static final String displayNameColumnName = "display_name";

    public static final String nameColumnName = "name";

  }

}
