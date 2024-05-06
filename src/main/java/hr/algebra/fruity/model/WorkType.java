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
@Table(name = WorkType.Constants.tableName)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class WorkType {

  @Id
  @Column(name = Constants.idColumnName)
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @EqualsAndHashCode.Include
  @ToString.Include
  private Integer id;

  @Column(name = Constants.uuidColumnName, nullable = false, unique = true)
  @EqualsAndHashCode.Include
  private UUID uuid;

  @Column(name = Constants.nameColumnName, length = 35, nullable = false, unique = true)
  @ToString.Include
  private @NonNull String name;

  @Column(name = Constants.displayNameColumnName, length = 35, nullable = false, unique = true)
  @ToString.Include
  private @NonNull String displayName;

  @Column(name = Constants.workersTabColumnName, nullable = false)
  private @NonNull Boolean workersTab;

  @Column(name = Constants.rowsTabColumnName, nullable = false)
  private @NonNull Boolean rowsTab;

  @Column(name = Constants.equipmentsTabColumnName, nullable = false)
  private @NonNull Boolean equipmentsTab;

  @Column(name = Constants.attachmentsTabColumnName, nullable = false)
  private @NonNull Boolean attachmentsTab;

  @Column(name = Constants.agentsTabColumnName, nullable = false)
  private @NonNull Boolean agentsTab;

  @Column(name = Constants.quantitiesTabColumnName, nullable = false)
  private @NonNull Boolean quantitiesTab;

  @OneToMany(mappedBy = Work.Fields.type, fetch = FetchType.LAZY)
  @Singular
  private Set<Work> works;

  @PrePersist
  private void prePersist() {
    this.uuid = UUID.randomUUID();
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String name = "name";

    public static final String displayName = "displayName";

    public static final String workersTab = "workersTab";

    public static final String rowsTab = "rowsTab";

    public static final String equipmentsTab = "equipmentsTab";

    public static final String attachmentsTab = "attachmentsTab";

    public static final String agentsTab = "agentsTab";

    public static final String quantitiesTab = "quantitiesTab";

    public static final String works = "works";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "work_types";

    public static final String joinColumnName = "work_type_fk";

    public static final String idColumnName = "work_type_id";

    public static final String uuidColumnName = "uuid";

    public static final String nameColumnName = "name";

    public static final String displayNameColumnName = "display_name";

    public static final String workersTabColumnName = "workers_tab";

    public static final String rowsTabColumnName = "rows_tab";

    public static final String equipmentsTabColumnName = "equipments_tab";

    public static final String attachmentsTabColumnName = "attachments_tab";

    public static final String agentsTabColumnName = "agents_tab";

    public static final String quantitiesTabColumnName = "quantities_tab";

  }

}
