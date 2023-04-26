package hr.algebra.fruity.repository;

import hr.algebra.fruity.model.Agent;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.model.WorkAgent;
import hr.algebra.fruity.model.WorkAgent.WorkAgentId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkAgentRepository extends JpaRepository<WorkAgent, WorkAgentId> {

  Optional<WorkAgent> findByWorkIdAndAgentId(Long workFk, Integer agentFk);

  Optional<WorkAgent> findByWorkAndAgent(Work work, Agent agent);

  List<WorkAgent> findAllByWorkId(Long workFk);

  List<WorkAgent> findAllByWork(Work work);

  boolean existsByWorkIdAndAgentId(Long workFk, Integer agentFk);

  boolean existsByWorkAndAgent(Work work, Agent agent);

}
