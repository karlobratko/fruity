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
@Table(name = Agent.Constants.tableName)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class Agent {

  @Id
  @Column(name = Constants.idColumnName)
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @EqualsAndHashCode.Include
  @ToString.Include
  private Integer id;

  @Column(name = Constants.uuidColumnName, nullable = false, unique = true)
  @EqualsAndHashCode.Include
  private UUID uuid;

  @Column(name = Constants.manufacturerColumnName, length = 50)
  @ToString.Include
  private String manufacturer;

  @Column(name = Constants.nameColumnName, length = 50, nullable = false)
  @ToString.Include
  private @NonNull String name;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = AgentState.Constants.joinColumnName, nullable = false)
  private @NonNull AgentState state;

  @OneToMany(mappedBy = WorkAgent.Fields.agent, fetch = FetchType.LAZY)
  @Singular
  private Set<WorkAgent> works;

  @OneToMany(mappedBy = RealisationAgent.Fields.agent, fetch = FetchType.LAZY)
  @Singular
  private Set<RealisationAgent> realisations;

  @PrePersist
  private void prePersist() {
    this.uuid = UUID.randomUUID();
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String manufacturer = "manufacturer";

    public static final String name = "name";

    public static final String state = "state";

    public static final String works = "works";

    public static final String realisations = "realisations";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "agents";

    public static final String joinColumnName = "agent_fk";

    public static final String idColumnName = "agent_id";

    public static final String uuidColumnName = "uuid";

    public static final String manufacturerColumnName = "manufacturer";

    public static final String nameColumnName = "name";

  }

}
