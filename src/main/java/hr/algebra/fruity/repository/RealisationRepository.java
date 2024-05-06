package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.Realisation;
import hr.algebra.fruity.model.User;
import hr.algebra.fruity.model.Work;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealisationRepository extends JpaRepository<Realisation, Long> {

  Optional<Realisation> findByIdAndWorkUser(Long id, User user);

  Optional<Realisation> findByIdAndWorkUserId(Long id, Long userFk);

  Set<Realisation> findAllByWorkUser(User user);

  Set<Realisation> findAllByWorkUserId(Long userFk);

  Set<Realisation> findAllByWork(Work work);

  Set<Realisation> findAllByWorkId(Long workFk);

  Optional<Realisation> findByWorkAndEmployeeAndStartDateTimeAndEndDateTime(Work work, Employee employee, LocalDateTime startDateTime, LocalDateTime endDateTime);

  Optional<Realisation> findByWorkIdAndEmployeeIdAndStartDateTimeAndEndDateTime(Long workFk, Long employeeFk, LocalDateTime startDateTime, LocalDateTime endDateTime);

  boolean existsByWorkAndEmployeeAndStartDateTimeAndEndDateTime(Work work, Employee employee, LocalDateTime startDateTime, LocalDateTime endDateTime);

  boolean existsByWorkIdAndEmployeeIdAndStartDateTimeAndEndDateTime(Long workFk, Long employeeFk, LocalDateTime startDateTime, LocalDateTime endDateTime);

  void deleteByEmployeeIdAndWorkFinishedFalse(Long employeeFk);

  void deleteByEmployeeAndWorkFinishedFalse(Employee employee);

}
