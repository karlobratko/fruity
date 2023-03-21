package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends PagingAndSortingRepository<Equipment, Long>, JpaRepository<Equipment, Long> {
}
