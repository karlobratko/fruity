package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Work;
import java.util.Collection;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends PagingAndSortingRepository<Work, Long>, JpaRepository<Work, Long> {
  Set<Work> findAllByUserId(Long userId);
}
