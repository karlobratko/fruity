package hr.algebra.fruity.controller;

import hr.algebra.fruity.dto.request.CreateRealisationAgentRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationAgentRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.FullRealisationAgentResponseDto;
import hr.algebra.fruity.dto.response.RealisationAgentResponseDto;
import hr.algebra.fruity.service.RealisationAgentService;
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
@RequestMapping(RealisationAgentController.Mappings.requestMapping)
@RequiredArgsConstructor
public class RealisationAgentController {

  private final RealisationAgentService realisationAgentService;

  @GetMapping(Mappings.getAllRealisationAgentsGetMapping)
  public ResponseEntity<ApiResponse<List<RealisationAgentResponseDto>>> getAllRealisationAgentsByRealisationId(@PathVariable Long realisationFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationAgentService.getAllRealisationAgentsByRealisationId(realisationFk),
        "Sredstva korištena u realizaciji uspješno dohvaćena."
      )
    );
  }

  @GetMapping(Mappings.getRealisationAgentByIdGetMapping)
  public ResponseEntity<ApiResponse<FullRealisationAgentResponseDto>> getRealisationAgentByRealisationIdAndAgentId(@PathVariable Long realisationFk, @PathVariable Integer agentFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationAgentService.getRealisationAgentByRealisationIdAndAgentId(realisationFk, agentFk),
        "Sredstvo korišteno u realizaciji uspješno dohvaćeno."
      )
    );
  }

  @PostMapping(Mappings.createRealisationAgentPostMapping)
  public ResponseEntity<ApiResponse<FullRealisationAgentResponseDto>> createRealisationAgentForRealisationId(@PathVariable Long realisationFk, @Valid @RequestBody CreateRealisationAgentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationAgentService.createRealisationAgentForRealisationId(realisationFk, requestDto),
        "Sredstvo uspješno dodano u realizaciju."
      )
    );
  }

  @PutMapping(Mappings.updateRealisationAgentByIdPutMapping)
  public ResponseEntity<ApiResponse<FullRealisationAgentResponseDto>> updateRealisationAgentByRealisationIdAndAgentId(@PathVariable Long realisationFk, @PathVariable Integer agentFk, @Valid @RequestBody UpdateRealisationAgentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationAgentService.updateRealisationAgentByRealisationIdAndAgentId(realisationFk, agentFk, requestDto),
        "Sredstvo korišteno u realizaciji uspješno promijenjeno."
      )
    );
  }

  @DeleteMapping(Mappings.deleteRealisationAgentByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteRealisationAgentByRealisationIdAndAgentId(@PathVariable Long realisationFk, @PathVariable Integer agentFk) {
    realisationAgentService.deleteRealisationAgentByRealisationIdAndAgentId(realisationFk, agentFk);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Sredstvo korišteno u realizaciji uspješno obrisano."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = RealisationController.Mappings.requestMapping + "/{realisationFk}/agents";

    public static final String getAllRealisationAgentsGetMapping = "";

    public static final String getRealisationAgentByIdGetMapping = "/{agentFk}";

    public static final String createRealisationAgentPostMapping = "";

    public static final String updateRealisationAgentByIdPutMapping = "/{agentFk}";

    public static final String deleteRealisationAgentByIdDeleteMapping = "/{agentFk}";

  }

}