package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.User;
import hr.algebra.fruity.model.Work;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends PagingAndSortingRepository<Work, Long>, JpaRepository<Work, Long> {

  Optional<Work> findByIdAndUser(Long id, User user);

  Optional<Work> findByIdAndUserId(Long id, Long userId);

  Set<Work> findAllByUser(User user);

  Set<Work> findAllByUserId(Long userId);

}
