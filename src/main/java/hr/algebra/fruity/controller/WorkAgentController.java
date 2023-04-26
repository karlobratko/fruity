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
  public ResponseEntity<ApiResponse<List<WorkAgentResponseDto>>> getAllWorkAgentsByWorkId(@PathVariable Long workFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workService.getAllWorkAgentsByWorkId(workFk),
        "Sredstva korištena u radu uspješno dohvaćena."
      )
    );
  }

  @GetMapping(Mappings.getWorkAgentByIdGetMapping)
  public ResponseEntity<ApiResponse<FullWorkAgentResponseDto>> getWorkAgentByWorkIdAndAgentId(@PathVariable Long workFk, @PathVariable Integer agentFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workService.getWorkAgentByWorkIdAndAgentId(workFk, agentFk),
        "Sredstvo korišteno u radu uspješno dohvaćeno."
      )
    );
  }

  @PostMapping(Mappings.createWorkAgentPostMapping)
  public ResponseEntity<ApiResponse<FullWorkAgentResponseDto>> createWorkAgentForWorkId(@PathVariable Long workFk, @Valid @RequestBody CreateWorkAgentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workService.createWorkAgentForWorkId(workFk, requestDto),
        "Sredstvo uspješno dodano u rad."
      )
    );
  }

  @PutMapping(Mappings.updateWorkAgentByIdPutMapping)
  public ResponseEntity<ApiResponse<FullWorkAgentResponseDto>> updateWorkAgentByWorkIdAndAgentId(@PathVariable Long workFk, @PathVariable Integer agentFk, @Valid @RequestBody UpdateWorkAgentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workService.updateWorkAgentByWorkIdAndAgentId(workFk, agentFk, requestDto),
        "Sredstvo korišteno u radu uspješno promijenjeno."
      )
    );
  }

  @DeleteMapping(Mappings.deleteWorkAgentByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteWorkAgentByWorkIdAndAgentId(@PathVariable Long workFk, @PathVariable Integer agentFk) {
    workService.deleteWorkAgentByWorkIdAndAgentId(workFk, agentFk);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Sredstvo korišteno u radu uspješno obrisano."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = WorkController.Mappings.requestMapping + "/{workFk}/agents";

    public static final String getAllWorkAgentsGetMapping = "";

    public static final String getWorkAgentByIdGetMapping = "/{agentFk}";

    public static final String createWorkAgentPostMapping = "";

    public static final String updateWorkAgentByIdPutMapping = "/{agentFk}";

    public static final String deleteWorkAgentByIdDeleteMapping = "/{agentFk}";

  }

}