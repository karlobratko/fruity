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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = WorkAttachment.Constants.tableName)
@NoArgsConstructor(force = true)
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE works_attachments SET delete_date = CURRENT_DATE WHERE attachment_fk = ? AND work_fk = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "delete_date IS NULL")
public class WorkAttachment {

  @EmbeddedId
  @EqualsAndHashCode.Include
  @ToString.Include
  private WorkAttachmentId id;

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

  @MapsId(WorkAttachmentId.Fields.workFk)
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = Work.Constants.joinColumnName, nullable = false)
  private @NonNull Work work;

  @MapsId(WorkAttachmentId.Fields.attachmentFk)
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = Attachment.Constants.joinColumnName, nullable = false)
  private @NonNull Attachment attachment;

  @Column(name = Constants.costPerHourColumnName, precision = 15, scale = 6, nullable = false)
  private BigDecimal costPerHour;

  @Column(name = Constants.noteColumnName, length = 500)
  private String note;

  @Builder
  public WorkAttachment(WorkAttachmentId id, @NonNull Work work, @NonNull Attachment attachment, BigDecimal costPerHour, String note) {
    this.id = Objects.requireNonNullElse(id, new WorkAttachmentId(work.getId(), attachment.getId()));
    this.work = work;
    this.attachment = attachment;
    this.costPerHour = Objects.requireNonNullElse(costPerHour, attachment.getCostPerHour());
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
  public static class WorkAttachmentId implements Serializable {

    @Column(name = Work.Constants.joinColumnName, nullable = false)
    private @NonNull Long workFk;

    @Column(name = Attachment.Constants.joinColumnName, nullable = false)
    private @NonNull Long attachmentFk;


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Fields {

      public static final String workFk = "workFk";

      public static final String attachmentFk = "attachmentFk";

    }

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String work = "work";

    public static final String attachment = "attachment";

    public static final String costPerHour = "costPerHour";

    public static final String note = "note";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "works_attachments";

    public static final String joinColumnName = "work_attachment_fk";

    public static final String uuidColumnName = "uuid";

    public static final String createDateColumnName = "create_date";

    public static final String updateDateColumnName = "update_date";

    public static final String deleteDateColumnName = "delete_date";

    public static final String costPerHourColumnName = "cost_per_hour";

    public static final String noteColumnName = "note";

  }

}
