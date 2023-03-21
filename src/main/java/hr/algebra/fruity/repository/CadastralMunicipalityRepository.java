package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.CadastralMunicipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadastralMunicipalityRepository extends JpaRepository<CadastralMunicipality, Integer> {
}
