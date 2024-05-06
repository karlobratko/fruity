package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.FruitCultivar;
import hr.algebra.fruity.model.HarvestedFruitClass;
import hr.algebra.fruity.model.Realisation;
import hr.algebra.fruity.model.RealisationHarvest;
import hr.algebra.fruity.model.RealisationHarvest.RealisationHarvestId;
import hr.algebra.fruity.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealisationHarvestRepository extends JpaRepository<RealisationHarvest, RealisationHarvestId> {

  Optional<RealisationHarvest> findByRealisationIdAndFruitCultivarIdAndHarvestedFruitClassIdAndRealisationWorkUserId(Long realisationFk, Integer fruitCultivarFk, Integer harvestedFruitClassFk, Long userFk);

  Optional<RealisationHarvest> findByRealisationAndFruitCultivarAndHarvestedFruitClassAndRealisationWorkUser(Realisation realisation, FruitCultivar fruitCultivar, HarvestedFruitClass harvestedFruitClass, User user);

  List<RealisationHarvest> findAllByRealisationId(Long realisationFk);

  List<RealisationHarvest> findAllByRealisation(Realisation realisation);

  List<RealisationHarvest> findAllByRealisationIdAndFruitCultivarId(Long realisationFk, Integer fruitCultivarFk);

  List<RealisationHarvest> findAllByRealisationAndFruitCultivar(Realisation realisation, FruitCultivar fruitCultivar);

  boolean existsByRealisationIdAndFruitCultivarIdAndHarvestedFruitClassId(Long realisationFk, Integer fruitCultivarFk, Integer harvestedFruitClassFk);

  boolean existsByRealisationAndFruitCultivarAndHarvestedFruitClass(Realisation realisation, FruitCultivar fruitCultivar, HarvestedFruitClass harvestedFruitClass);

}
