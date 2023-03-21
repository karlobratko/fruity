package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.FruitCultivar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FruitCultivarRepository extends JpaRepository<FruitCultivar, Integer> {
}
