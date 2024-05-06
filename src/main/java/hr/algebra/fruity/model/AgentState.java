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
@Table(name = AgentState.Constants.tableName)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class AgentState {

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
  private @NonNull String displayName;

  @OneToMany(mappedBy = Agent.Fields.state, fetch = FetchType.LAZY)
  @Singular
  private Set<Agent> agents;

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

    public static final String agents = "agents";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "agent_states";

    public static final String joinColumnName = "agent_state_fk";

    public static final String idColumnName = "agent_state_id";

    public static final String uuidColumnName = "uuid";

    public static final String nameColumnName = "name";

    public static final String displayNameColumnName = "display_name";

  }

}
