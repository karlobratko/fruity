package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.HarvestedFruitClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HarvestedFruitClassRepository extends JpaRepository<HarvestedFruitClass, Integer> {
}
