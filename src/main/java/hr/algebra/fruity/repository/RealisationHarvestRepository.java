package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.RealisationHarvest;
import hr.algebra.fruity.model.RealisationHarvest.RealisationHarvestId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealisationHarvestRepository extends JpaRepository<RealisationHarvest, RealisationHarvestId> {
}
