package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaRepository<User, Long> {

  Optional<User> findByOib(String oib);

  Boolean existsByOib(String oib);

}
