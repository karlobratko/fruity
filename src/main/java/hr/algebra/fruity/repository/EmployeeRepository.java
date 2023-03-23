package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>, JpaRepository<Employee, Long> {

  Optional<Employee> findByUsername(String username);

  Boolean existsByEmail(String email);

}
