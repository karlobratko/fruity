package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.County;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountyRepository extends JpaRepository<County, Integer> {

  Optional<County> findByName(String name);

  Optional<County> findByAbbreviation(String abbreviation);

}
