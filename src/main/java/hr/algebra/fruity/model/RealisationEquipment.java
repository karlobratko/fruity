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
@Table(name = RealisationEquipment.Constants.tableName)
@NoArgsConstructor(force = true)
@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class RealisationEquipment {

  @EmbeddedId
  @EqualsAndHashCode.Include
  @ToString.Include
  private RealisationEquipmentId id;

  @MapsId(RealisationEquipmentId.Fields.realisationFk)
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = Realisation.Constants.joinColumnName, nullable = false)
  private @NonNull Realisation realisation;

  @MapsId(RealisationEquipmentId.Fields.equipmentFk)
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = Equipment.Constants.joinColumnName, nullable = false)
  private @NonNull Equipment equipment;

  @Column(name = Constants.costPerHourColumnName, precision = 15, scale = 6, nullable = false)
  private BigDecimal costPerHour;

  @Column(name = Constants.noteColumnName, length = 500)
  private String note;

  @Builder
  public RealisationEquipment(RealisationEquipmentId id, @NonNull Realisation realisation, @NonNull Equipment equipment, BigDecimal costPerHour, String note) {
    this.id = Objects.requireNonNullElse(id, new RealisationEquipmentId(realisation.getId(), equipment.getId()));
    this.realisation = realisation;
    this.equipment = equipment;
    this.costPerHour = Objects.requireNonNullElse(costPerHour, equipment.getCostPerHour());
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
  public static class RealisationEquipmentId implements Serializable {

    @Column(name = Realisation.Constants.joinColumnName, nullable = false)
    private @NonNull Long realisationFk;

    @Column(name = Equipment.Constants.joinColumnName, nullable = false)
    private @NonNull Long equipmentFk;


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Fields {

      public static final String realisationFk = "realisationFk";

      public static final String equipmentFk = "equipmentFk";

    }

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String realisation = "realisation";

    public static final String equipment = "equipment";

    public static final String costPerHour = "costPerHour";

    public static final String note = "note";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String tableName = "realisations_equipments";

    public static final String joinColumnName = "realisation_equipment_fk";

    public static final String uuidColumnName = "uuid";

    public static final String costPerHourColumnName = "cost_per_hour";

    public static final String noteColumnName = "note";

  }

}
