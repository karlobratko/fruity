package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.AgentState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentStateRepository extends JpaRepository<AgentState, Integer> {
}
