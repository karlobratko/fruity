package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.User;
import hr.algebra.fruity.model.Work;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends PagingAndSortingRepository<Work, Long>, JpaRepository<Work, Long> {

  Optional<Work> findByIdAndUser(Long id, User user);

  Optional<Work> findByIdAndUserId(Long id, Long userId);

  Set<Work> findAllByUser(User user);

  Set<Work> findAllByUserId(Long userId);

  @Query(
    value = "SELECT we.work " +
      "FROM WorkEmployee we " +
      "WHERE we.employee = :employee " +
      "  AND we.work.finished = false " +
      "ORDER BY we.work.id ASC"
  )
  Set<Work> findAllByEmployee(Employee employee);

  @Query(
    value = "SELECT we.work " +
      "FROM WorkEmployee we " +
      "WHERE we.employee.id = :employeeFk " +
      "  AND we.work.finished = false " +
      "ORDER BY we.work.id ASC"
  )
  Set<Work> findAllByEmployeeId(Long employeeFk);

}
