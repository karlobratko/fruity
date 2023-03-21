package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.WorkEmployee;
import hr.algebra.fruity.model.WorkEmployee.WorkEmployeeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkEmployeeRepository extends JpaRepository<WorkEmployee, WorkEmployeeId> {
}
