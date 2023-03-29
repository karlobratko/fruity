package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.request.UpdateUserRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.FullUserResponseDto;
import hr.algebra.fruity.dto.response.UserResponseDto;
import hr.algebra.fruity.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserController.Mappings.requestMapping)
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping(Mappings.getCurrentUserGetMapping)
  public ResponseEntity<ApiResponse<FullUserResponseDto>> getCurrentUser() {
    return ResponseEntity.ok(
      ApiResponse.ok(
        userService.getCurrentUser(),
        "Prijavljeni korisnik uspješno dohvaćen."
      )
    );
  }

  @PutMapping(Mappings.updateCurrentUserPutMapping)
  public ResponseEntity<ApiResponse<UserResponseDto>> updateCurrentUser(@Valid @RequestBody UpdateUserRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        userService.updateCurrentUser(requestDto),
        "Prijavljeni korisnik uspješno promijenjen."
      )
    );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiUserManagementRequestMapping + "/users";

    public static final String getCurrentUserGetMapping = "";

    public static final String updateCurrentUserPutMapping = "";

  }

}
