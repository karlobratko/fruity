package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.response.AgentResponseDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.service.AgentService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AgentController.Mappings.requestMapping)
@RequiredArgsConstructor
public class AgentController {

  private final AgentService agentService;

  @GetMapping(Mappings.getAllAgentsGetMapping)
  public ResponseEntity<ApiResponse<List<AgentResponseDto>>> getAllAgents() {
    return ResponseEntity.ok(
      ApiResponse.ok(
        agentService.getAllAgents(),
        "Sredstva uspješno dohvaćena."
      )
    );
  }

  @GetMapping(Mappings.getAgentByIdGetMapping)
  public ResponseEntity<ApiResponse<AgentResponseDto>> getAgentById(@PathVariable Integer id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        agentService.getAgentById(id),
        "Sredstvo uspješno dohvaćeno."
      )
    );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiResourceManagementRequestMapping + "/agents";

    public static final String getAllAgentsGetMapping = "";

    public static final String getAgentByIdGetMapping = "/{id}";

  }

}
