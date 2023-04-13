package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.model.CadastralParcel;
import hr.algebra.fruity.model.User;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArcodeParcelRepository extends PagingAndSortingRepository<ArcodeParcel, Long>, JpaRepository<ArcodeParcel, Long> {

  Set<ArcodeParcel> findAllByUser(User user);

  Set<ArcodeParcel> findAllByUserId(Long userFk);


  Set<ArcodeParcel> findAllByCadastralParcel(CadastralParcel cadastralParcel);

  Set<ArcodeParcel> findAllByCadastralParcelId(Long cadastralParcelFk);

  Optional<ArcodeParcel> findByNameAndCadastralParcelId(String name, Long cadastralParcelFk);

  Optional<ArcodeParcel> findByArcode(Integer arcode);

}
