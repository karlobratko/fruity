package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.RealisationAgent;
import hr.algebra.fruity.model.RealisationAgent.RealisationAgentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealisationAgentRepository extends JpaRepository<RealisationAgent, RealisationAgentId> {
}
