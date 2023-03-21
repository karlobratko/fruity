package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.CadastralParcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadastralParcelRepository extends PagingAndSortingRepository<CadastralParcel, Long>, JpaRepository<CadastralParcel, Long> {
}
