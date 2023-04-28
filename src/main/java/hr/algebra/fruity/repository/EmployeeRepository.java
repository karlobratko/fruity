package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.EmployeeRole;
import hr.algebra.fruity.model.User;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>, JpaRepository<Employee, Long> {

  Optional<Employee> findByUsername(String username);

  Boolean existsByEmailAndRole(String email, EmployeeRole employeeRole);

  Boolean existsByEmailAndUser(String email, User user);

  Boolean existsByEmailAndUserId(String email, Long userFk);

  Boolean existsByUsername(String username);

  Optional<Employee> findByIdAndUser(Long id, User user);

  Optional<Employee> findByIdAndUserId(Long id, Long userFk);

  Set<Employee> findAllByUser(User user);

  Set<Employee> findAllByUserId(Long userFk);

  Optional<Employee> findByEmailAndUser(String email, User user);

  Optional<Employee> findByEmailAndUserId(String email, Long userFk);

}
