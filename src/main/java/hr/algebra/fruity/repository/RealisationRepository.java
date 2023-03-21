package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Realisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealisationRepository extends JpaRepository<Realisation, Long> {
}
