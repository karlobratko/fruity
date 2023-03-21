package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Row;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RowRepository extends PagingAndSortingRepository<Row, Long>, JpaRepository<Row, Long> {
}
