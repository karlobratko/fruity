package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.ArcodeParcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArcodeParcelRepository extends PagingAndSortingRepository<ArcodeParcel, Long>, JpaRepository<ArcodeParcel, Long> {
}
