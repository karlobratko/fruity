package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Agent;
import hr.algebra.fruity.model.Realisation;
import hr.algebra.fruity.model.RealisationAgent;
import hr.algebra.fruity.model.RealisationAgent.RealisationAgentId;
import hr.algebra.fruity.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealisationAgentRepository extends JpaRepository<RealisationAgent, RealisationAgentId> {

  Optional<RealisationAgent> findByRealisationIdAndAgentIdAndRealisationWorkUserId(Long realisationFk, Integer agentFk, Long userFk);

  Optional<RealisationAgent> findByRealisationAndAgentAndRealisationWorkUser(Realisation realisation, Agent agent, User user);

  List<RealisationAgent> findAllByRealisationId(Long realisationFk);

  List<RealisationAgent> findAllByRealisation(Realisation realisation);

  boolean existsByRealisationIdAndAgentId(Long realisationFk, Integer agentFk);

  boolean existsByRealisationAndAgent(Realisation realisation, Agent agent);

}
