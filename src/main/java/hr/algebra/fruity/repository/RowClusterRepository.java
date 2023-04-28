package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.model.RowCluster;
import hr.algebra.fruity.model.User;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RowClusterRepository extends PagingAndSortingRepository<RowCluster, Long>, JpaRepository<RowCluster, Long> {

  Optional<RowCluster> findByIdAndUser(Long id, User user);

  Optional<RowCluster> findByIdAndUserId(Long id, Long userFk);

  Set<RowCluster> findAllByUser(User user);

  Set<RowCluster> findAllByUserId(Long userFk);

  Set<RowCluster> findAllByArcodeParcel(ArcodeParcel cadastralParcel);

  Set<RowCluster> findAllByArcodeParcelId(Long cadastralParcelFk);

  Optional<RowCluster> findByNameAndArcodeParcel(String name, ArcodeParcel arcodeParcel);

  Optional<RowCluster> findByNameAndArcodeParcelId(String name, Long cadastralParcelFk);

}
