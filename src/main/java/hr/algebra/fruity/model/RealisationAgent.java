package hr.algebra.fruity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = RealisationAgent.Constants.tableName)
@NoArgsConstructor(force = true)
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class RealisationAgent {

  @EmbeddedId
  @EqualsAndHashCode.Include
  @ToString.Include
  private RealisationAgentId id;

  @MapsId(RealisationAgentId.Fields.realisationFk)
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = Realisation.Constants.joinColumnName, nullable = false)
  private @NonNull Realisation realisation;

  @MapsId(RealisationAgentId.Fields.agentFk)
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = Agent.Constants.joinColumnName, nullable = false)
  private @NonNull Agent agent;

  @Column(name = Constants.agentQuantityColumnName, precision = 18, scale = 9, nullable = false)
  private @NonNull BigDecimal agentQuantity;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = Constants.agentUnitOfMeasureColumnName, nullable = false)
  private @NonNull UnitOfMeasure agentUnitOfMeasure;

  @Column(name = Constants.costPerHourColumnName, precision = 15, scale = 6, nullable = false)
  private @NonNull BigDecimal costPerUnitOfMeasure;

  @Column(name = Constants.waterQuantityColumnName, precision = 18, scale = 9, nullable = false)
  private @NonNull BigDecimal waterQuantity;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = Constants.waterUnitOfMeasureColumnName, nullable = false)
  private @NonNull UnitOfMeasure waterUnitOfMeasure;

  @Column(name = Constants.noteColumnName, length = 500)
  private String note;

  @Builder
  public RealisationAgent(RealisationAgentId id, @NonNull Realisation realisation, @NonNull Agent agent, @NonNull BigDecimal agentQuantity, @NonNull UnitOfMeasure agentUnitOfMeasure, @NonNull BigDecimal costPerUnitOfMeasure, @NonNull BigDecimal waterQuantity, @NonNull UnitOfMeasure waterUnitOfMeasure, String note) {
    this.id = Objects.requireNonNullElse(id, new RealisationAgentId(realisation.getId(), agent.getId()));
    this.realisation = realisation;
    this.agent = agent;
    this.agentQuantity = agentQuantity;
    this.agentUnitOfMeasure = agentUnitOfMeasure;
    this.costPerUnitOfMeasure = costPerUnitOfMeasure;
    this.waterQuantity = waterQuantity;
    this.waterUnitOfMeasure = waterUnitOfMeasure;
    this.note = note;
  }

  @Embeddable
  @NoArgsConstructor(force = true)
  @AllArgsConstructor
  @Builder
  @Getter
  @Setter
  @EqualsAndHashCode(doNotUseGetters = true)
  @ToString(doNotUseGetters = true)
  public static class RealisationAgentId implements Serializable {

    @Column(name = Realisation.Constants.joinColumnName, nullable = false)
    private @NonNull Long realisationFk;

    @Column(name = Agent.Constants.joinColumnName, nullable = false)
    private @NonNull Integer agentFk;


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Fields {

      public static final String realisationFk = "realisationFk";

      public static final String agentFk = "agentFk";

    }

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String realisation = "realisation";

    public static final String agent = "agent";

    public static final String agentQuantity = "agentQuantity";

    public static final String agentUnitOfMeasure = "agentUnitOfMeasure";

    public static final String costPerUnitOfMeasure = "costPerUnitOfMeasure";

    public static final String waterQuantity = "waterQuantity";

    public static final String waterUnitOfMeasure = "waterUnitOfMeasure";

    public static final String note = "note";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "realisations_agents";

    public static final String joinColumnName = "realisation_agent_fk";

    public static final String uuidColumnName = "uuid";

    public static final String agentQuantityColumnName = "agent_quantity";

    public static final String agentUnitOfMeasureColumnName = "agent_unit_of_measure_fk";

    public static final String waterQuantityColumnName = "water_quantity";

    public static final String waterUnitOfMeasureColumnName = "water_unit_of_measure_fk";

    public static final String costPerHourColumnName = "cost_per_unit_of_measure";

    public static final String noteColumnName = "note";

  }

}
