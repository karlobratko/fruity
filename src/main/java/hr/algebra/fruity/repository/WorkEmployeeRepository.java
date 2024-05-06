package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.User;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.model.WorkEmployee;
import hr.algebra.fruity.model.WorkEmployee.WorkEmployeeId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkEmployeeRepository extends JpaRepository<WorkEmployee, WorkEmployeeId> {

  Optional<WorkEmployee> findByWorkIdAndEmployeeIdAndWorkUserId(Long workFk, Long employeeFk, Long userFk);

  Optional<WorkEmployee> findByWorkAndEmployeeAndWorkUser(Work work, Employee employee, User user);

  List<WorkEmployee> findAllByWorkId(Long workFk);

  List<WorkEmployee> findAllByWork(Work work);

  boolean existsByWorkIdAndEmployeeId(Long workFk, Long employeeFk);

  boolean existsByWorkAndEmployee(Work work, Employee employee);

  void deleteByEmployeeIdAndWorkFinishedFalse(Long employeeFk);

  void deleteByEmployeeAndWorkFinishedFalse(Employee employee);

}
