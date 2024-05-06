package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.EmployeeRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Integer> {

  Optional<EmployeeRole> findByName(String name);

}
