package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.model.CadastralParcel;
import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.model.RowCluster;
import hr.algebra.fruity.model.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RowRepository extends PagingAndSortingRepository<Row, Long>, JpaRepository<Row, Long> {

  Optional<Row> findByIdAndUser(Long id, User user);

  Optional<Row> findByIdAndUserId(Long id, Long userFk);

  @Query(
    value = "SELECT r " +
      "FROM Row r " +
      "WHERE r.id IN :ids " +
      "  AND r.user.id = :userFk " +
      "ORDER BY r.id ASC"
  )
  List<Row> findAllByIdsAndUserId(List<Long> ids, Long userFk);

  @Query(
    value = "SELECT r " +
      "FROM Row r " +
      "WHERE r.id IN :ids " +
      "  AND r.user = :user " +
      "ORDER BY r.id ASC"
  )
  List<Row> findAllByIdsAndUser(List<Long> ids, User user);

  @Query(
    value = "SELECT r " +
      "FROM Row r " +
      "WHERE r.rowCluster = :rowCluster " +
      "  AND r.user.id = :userFk " +
      "ORDER BY r.id ASC"
  )
  List<Row> findAllByRowClusterAndUserId(RowCluster rowCluster, Long userFk);

  @Query(
    value = "SELECT r " +
      "FROM Row r " +
      "WHERE r.rowCluster.id = :rowClusterFk " +
      "  AND r.user = :user " +
      "ORDER BY r.id ASC"
  )
  List<Row> findAllByRowClusterAndUser(Long rowClusterFk, User user);

  @Query(
    value = "SELECT r " +
      "FROM Row r " +
      "WHERE r.rowCluster.arcodeParcel = :arcodeParcel " +
      "  AND r.user.id = :userFk " +
      "ORDER BY r.id ASC"
  )
  List<Row> findAllByArcodeParcelAndUserId(ArcodeParcel arcodeParcel, Long userFk);

  @Query(
    value = "SELECT r " +
      "FROM Row r " +
      "WHERE r.rowCluster.arcodeParcel.id = :arcodeParcelFk " +
      "  AND r.user = :user " +
      "ORDER BY r.id ASC"
  )
  List<Row> findAllByArcodeParcelAndUser(Long arcodeParcelFk, User user);

  @Query(
    value = "SELECT r " +
      "FROM Row r " +
      "WHERE r.rowCluster.arcodeParcel.cadastralParcel = :cadastralParcel " +
      "  AND r.user.id = :userFk " +
      "ORDER BY r.id ASC"
  )
  List<Row> findAllByCadastralParcelAndUserId(CadastralParcel cadastralParcel, Long userFk);

  @Query(
    value = "SELECT r " +
      "FROM Row r " +
      "WHERE r.rowCluster.arcodeParcel.cadastralParcel.id = :cadastralParcelFk " +
      "  AND r.user = :user " +
      "ORDER BY r.id ASC"
  )
  List<Row> findAllByCadastralParcelAndUser(Long cadastralParcelFk, User user);

  Set<Row> findAllByUser(User user);

  Set<Row> findAllByUserId(Long userFk);

  Set<Row> findAllByRowClusterOrderByOrdinalAsc(RowCluster rowCluster);

  Set<Row> findAllByRowClusterIdOrderByOrdinalAsc(Long rowClusterFk);

  Boolean existsByOrdinalAndRowCluster(Integer ordinal, RowCluster rowCluster);

  Boolean existsByOrdinalAndRowClusterId(Integer ordinal, Long rowClusterFk);

  Set<Row> findByOrdinalGreaterThanEqualAndRowClusterOrderByOrdinalDesc(Integer ordinal, RowCluster rowCluster);

  Set<Row> findByOrdinalGreaterThanEqualAndRowClusterIdOrderByOrdinalDesc(Integer ordinal, Long rowClusterFk);

  Set<Row> findByOrdinalGreaterThanEqualAndRowClusterOrderByOrdinalAsc(Integer ordinal, RowCluster rowCluster);

  Set<Row> findByOrdinalGreaterThanEqualAndRowClusterIdOrderByOrdinalAsc(Integer ordinal, Long rowClusterFk);

  Set<Row> findAllByRowCluster(RowCluster rowCluster);

  Set<Row> findAllByRowClusterId(Long rowClusterFk);

  Set<Row> findAllByRowClusterArcodeParcel(ArcodeParcel arcodeParcel);

  Set<Row> findAllByRowClusterArcodeParcelId(Long arcodeParcelFk);

  Set<Row> findAllByRowClusterArcodeParcelCadastralParcel(CadastralParcel cadastralParcel);

  Set<Row> findAllByRowClusterArcodeParcelCadastralParcelId(Long cadastralParcelFk);

}
