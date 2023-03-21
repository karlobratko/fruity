package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.WorkAgent;
import hr.algebra.fruity.model.WorkAgent.WorkAgentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkAgentRepository extends JpaRepository<WorkAgent, WorkAgentId> {
}
