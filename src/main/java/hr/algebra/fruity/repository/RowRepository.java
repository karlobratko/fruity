package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.model.RowCluster;
import hr.algebra.fruity.model.User;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RowRepository extends PagingAndSortingRepository<Row, Long>, JpaRepository<Row, Long> {

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

}
