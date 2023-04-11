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
import java.time.LocalDateTime;
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
@Table(name = Work.Constants.tableName)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE works SET delete_date = CURRENT_DATE WHERE work_id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "delete_date IS NULL")
public class Work {

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

  @Column(name = Constants.startDateTimeColumnName, nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @ToString.Include
  private @NonNull LocalDateTime startDateTime;

  @Column(name = Constants.endDateTimeColumnName, nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @ToString.Include
  private @NonNull LocalDateTime endDateTime;

  @Column(name = Constants.finishedColumnName, nullable = false)
  @Builder.Default
  private boolean finished = false;

  @Column(name = Constants.noteColumnName, length = 500)
  private String note;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = WorkType.Constants.joinColumnName, nullable = false)
  private @NonNull WorkType type;

  @OneToMany(mappedBy = WorkEmployee.Fields.work, fetch = FetchType.LAZY)
  @Singular
  private Set<WorkEmployee> employees;

  @OneToMany(mappedBy = WorkRow.Fields.work, fetch = FetchType.LAZY)
  @Singular
  private Set<WorkRow> rows;

  @OneToMany(mappedBy = WorkEquipment.Fields.work, fetch = FetchType.LAZY)
  @Singular
  private Set<WorkEquipment> equipments;

  @OneToMany(mappedBy = WorkAttachment.Fields.work, fetch = FetchType.LAZY)
  @Singular
  private Set<WorkAttachment> attachments;

  @OneToMany(mappedBy = WorkAgent.Fields.work, fetch = FetchType.LAZY)
  @Singular
  private Set<WorkAgent> agents;

  @OneToMany(mappedBy = Realisation.Fields.work, fetch = FetchType.LAZY)
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

    public static final String startDateTime = "startDateTime";

    public static final String endDateTime = "endDateTime";

    public static final String finished = "finished";

    public static final String note = "note";

    public static final String type = "type";

    public static final String employees = "employees";

    public static final String rows = "rows";

    public static final String equipments = "equipments";

    public static final String attachments = "attachments";

    public static final String agents = "agents";

    public static final String realisations = "realisations";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "works";

    public static final String joinColumnName = "work_fk";

    public static final String idColumnName = "work_id";

    public static final String uuidColumnName = "uuid";

    public static final String createDateColumnName = "create_date";

    public static final String updateDateColumnName = "update_date";

    public static final String deleteDateColumnName = "delete_date";

    public static final String startDateTimeColumnName = "start_date_time";

    public static final String endDateTimeColumnName = "end_date_time";

    public static final String finishedColumnName = "finished";

    public static final String noteColumnName = "note";

  }

}
