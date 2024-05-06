package hr.algebra.fruity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = WorkAgent.Constants.tableName)
@NoArgsConstructor(force = true)
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE works_agents SET delete_date = CURRENT_DATE WHERE agent_fk = ? AND work_fk = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "delete_date IS NULL")
public class WorkAgent {

  @EmbeddedId
  @EqualsAndHashCode.Include
  @ToString.Include
  private WorkAgentId id;

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

  @MapsId(WorkAgentId.Fields.workFk)
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = Work.Constants.joinColumnName, nullable = false)
  private @NonNull Work work;

  @MapsId(WorkAgentId.Fields.agentFk)
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
  public WorkAgent(WorkAgentId id, @NonNull Work work, @NonNull Agent agent, @NonNull BigDecimal agentQuantity, @NonNull UnitOfMeasure agentUnitOfMeasure, @NonNull BigDecimal costPerUnitOfMeasure, @NonNull BigDecimal waterQuantity, @NonNull UnitOfMeasure waterUnitOfMeasure, String note) {
    this.id = Objects.requireNonNullElse(id, new WorkAgentId(work.getId(), agent.getId()));
    this.work = work;
    this.agent = agent;
    this.agentQuantity = agentQuantity;
    this.agentUnitOfMeasure = agentUnitOfMeasure;
    this.costPerUnitOfMeasure = costPerUnitOfMeasure;
    this.waterQuantity = waterQuantity;
    this.waterUnitOfMeasure = waterUnitOfMeasure;
    this.note = note;
  }

  @PrePersist
  private void prePersist() {
    this.uuid = UUID.randomUUID();
  }

  @PreRemove
  private void preRemove() {
    this.deleteDate = LocalDate.now();
  }

  @Embeddable
  @NoArgsConstructor(force = true)
  @AllArgsConstructor
  @Builder
  @Getter
  @Setter
  @EqualsAndHashCode(doNotUseGetters = true)
  @ToString(doNotUseGetters = true)
  public static class WorkAgentId implements Serializable {

    @Column(name = Work.Constants.joinColumnName, nullable = false)
    private @NonNull Long workFk;

    @Column(name = Agent.Constants.joinColumnName, nullable = false)
    private @NonNull Integer agentFk;

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Fields {

      public static final String workFk = "workFk";

      public static final String agentFk = "agentFk";

    }

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String work = "work";

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

    public static final String tableName = "works_agents";

    public static final String joinColumnName = "work_agent_fk";

    public static final String uuidColumnName = "uuid";

    public static final String createDateColumnName = "create_date";

    public static final String updateDateColumnName = "update_date";

    public static final String deleteDateColumnName = "delete_date";

    public static final String agentQuantityColumnName = "agent_quantity";

    public static final String agentUnitOfMeasureColumnName = "agent_unit_of_measure_fk";

    public static final String waterQuantityColumnName = "water_quantity";

    public static final String waterUnitOfMeasureColumnName = "water_unit_of_measure_fk";

    public static final String costPerHourColumnName = "cost_per_unit_of_measure";

    public static final String noteColumnName = "note";

  }

}
