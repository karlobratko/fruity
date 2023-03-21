package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.EmployeePrivilege;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePrivilegeRepository extends JpaRepository<EmployeePrivilege, Integer> {

  Optional<EmployeePrivilege> findByName(String name);

}
