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
@Table(name = RealisationRow.Constants.tableName)
@NoArgsConstructor(force = true)
@Builder
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class RealisationRow {

  @EmbeddedId
  @EqualsAndHashCode.Include
  @ToString.Include
  private RealisationRowId id;

  @MapsId(RealisationRowId.Fields.realisationFk)
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = Realisation.Constants.joinColumnName, nullable = false)
  private @NonNull Realisation realisation;

  @MapsId(RealisationRowId.Fields.rowFk)
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = Row.Constants.joinColumnName, nullable = false)
  private @NonNull Row row;

  @Column(name = Constants.noteColumnName, length = 500)
  private String note;

  @Builder
  public RealisationRow(RealisationRowId id, @NonNull Realisation realisation, @NonNull Row row, String note) {
    this.id = Objects.requireNonNullElse(id, new RealisationRowId(realisation.getId(), row.getId()));
    this.realisation = realisation;
    this.row = row;
    this.note = note;
  }

  @Embeddable
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Getter
  @Setter
  @EqualsAndHashCode(doNotUseGetters = true)
  @ToString(doNotUseGetters = true)
  public static class RealisationRowId implements Serializable {

    @Column(name = Realisation.Constants.joinColumnName, nullable = false)
    private @NonNull Long realisationFk;

    @Column(name = Row.Constants.joinColumnName, nullable = false)
    private @NonNull Long rowFk;


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Fields {

      public static final String realisationFk = "realisationFk";

      public static final String rowFk = "rowFk";

    }

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String realisation = "realisation";

    public static final String row = "row";

    public static final String note = "note";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "realisations_rows";

    public static final String joinColumnName = "realisation_row_fk";

    public static final String uuidColumnName = "uuid";

    public static final String noteColumnName = "note";

  }

}
