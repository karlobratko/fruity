package hr.algebra.fruity.controller;

import hr.algebra.fruity.dto.request.CreateWorkAgentRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkAgentRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.FullWorkAgentResponseDto;
import hr.algebra.fruity.dto.response.WorkAgentResponseDto;
import hr.algebra.fruity.service.WorkAgentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WorkAgentController.Mappings.requestMapping)
@RequiredArgsConstructor
public class WorkAgentController {

  private final WorkAgentService workService;

  @GetMapping(Mappings.getAllWorkAgentsGetMapping)
  public ResponseEntity<ApiResponse<List<WorkAgentResponseDto>>> getAllWorkAgentsByWorkId(@PathVariable Long workId) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workService.getAllWorkAgentsByWorkId(workId),
        "Sredstva korištena u radu uspješno dohvaćena."
      )
    );
  }

  @GetMapping(Mappings.getWorkAgentByIdGetMapping)
  public ResponseEntity<ApiResponse<FullWorkAgentResponseDto>> getWorkAgentByWorkIdAndAgentId(@PathVariable Long workId, @PathVariable Integer agentId) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workService.getWorkAgentByWorkIdAndAgentId(workId, agentId),
        "Sredstvo korišteno u radu uspješno dohvaćeno."
      )
    );
  }

  @PostMapping(Mappings.createWorkAgentPostMapping)
  public ResponseEntity<ApiResponse<FullWorkAgentResponseDto>> createWorkAgentForWorkId(@PathVariable Long workId, @Valid @RequestBody CreateWorkAgentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workService.createWorkAgentForWorkId(workId, requestDto),
        "Sredstvo uspješno dodano u rad."
      )
    );
  }

  @PutMapping(Mappings.updateWorkAgentByIdPutMapping)
  public ResponseEntity<ApiResponse<FullWorkAgentResponseDto>> updateWorkAgentByWorkIdAndAgentId(@PathVariable Long workId, @PathVariable Integer agentId, @Valid @RequestBody UpdateWorkAgentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workService.updateWorkAgentByWorkIdAndAgentId(workId, agentId, requestDto),
        "Sredstvo korišteno u radu uspješno promijenjeno."
      )
    );
  }

  @DeleteMapping(Mappings.deleteWorkAgentByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteWorkAgentByWorkIdAndAgentId(@PathVariable Long workId, @PathVariable Integer agentId) {
    workService.deleteWorkAgentByWorkIdAndAgentId(workId, agentId);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Sredstvo korišteno u radu uspješno obrisano."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = WorkController.Mappings.requestMapping + "/{workId}/agents";

    public static final String getAllWorkAgentsGetMapping = "";

    public static final String getWorkAgentByIdGetMapping = "/{agentId}";

    public static final String createWorkAgentPostMapping = "";

    public static final String updateWorkAgentByIdPutMapping = "/{agentId}";

    public static final String deleteWorkAgentByIdDeleteMapping = "/{agentId}";

  }

}