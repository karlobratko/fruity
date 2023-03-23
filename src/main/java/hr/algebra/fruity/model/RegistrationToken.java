package hr.algebra.fruity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = RegistrationToken.Constants.tableName)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE RegistrationToken SET deleteDate = CURRENT_DATE WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = RegistrationToken.Constants.deleteDateColumnName + " IS NULL")
public class RegistrationToken {

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

  @Column(name = Constants.createDateTimeColumnName, nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createDateTime;

  @Column(name = Constants.expireDateTimeColumnName, nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime expireDateTime;

  @Column(name = Constants.confirmDateTimeColumnName)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime confirmDateTime;

  @OneToOne(mappedBy = Employee.Fields.registrationToken, fetch = FetchType.EAGER)
  private Employee employee;

  private void init() {
    this.createDateTime = LocalDateTime.now();
    this.expireDateTime = this.createDateTime.plusMinutes(15);
  }

  @PrePersist
  private void prePersist() {
    this.uuid = UUID.randomUUID();
    init();
  }

  @PreRemove
  private void preRemove() {
    this.deleteDate = LocalDate.now();
  }

  public boolean isConfirmed() {
    return Objects.nonNull(this.confirmDateTime);
  }

  public boolean isExpired() {
    return this.expireDateTime.isBefore(LocalDateTime.now());
  }

  public void reset() {
    init();
    this.confirmDateTime = null;
  }

  public void confirm() {
    this.confirmDateTime = LocalDateTime.now();
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String createDate = "createDate";

    public static final String updateDate = "updateDate";

    public static final String deleteDate = "deleteDate";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "registration_tokens";

    public static final String joinColumnName = "registration_token_fk";

    public static final String idColumnName = "registration_token_id";

    public static final String uuidColumnName = "uuid";

    public static final String createDateColumnName = "create_date";

    public static final String updateDateColumnName = "update_date";

    public static final String deleteDateColumnName = "delete_date";

    public static final String createDateTimeColumnName = "create_date_time";

    public static final String expireDateTimeColumnName = "expire_date_time";

    public static final String confirmDateTimeColumnName = "confirm_date_time";

  }

}
