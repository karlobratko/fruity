package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.RowCluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RowClusterRepository extends PagingAndSortingRepository<RowCluster, Long>, JpaRepository<RowCluster, Long> {
}
