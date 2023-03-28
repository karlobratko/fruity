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

  @GetMapping(Mappings.getUserByIdGetMapping)
  public ResponseEntity<ApiResponse<FullUserResponseDto>> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        userService.getUserById(id),
        "Korisnik uspješno dohvaćen."
      )
    );
  }

  @PutMapping(Mappings.updateUserByIdPutMapping)
  public ResponseEntity<ApiResponse<UserResponseDto>> updateUserById(@PathVariable Long id, @Valid @RequestBody UpdateUserRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        userService.updateUserById(id, requestDto),
        "Korisnik uspješno promijenjen."
      )
    );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiUserManagementRequestMapping + "/users";

    public static final String getUserByIdGetMapping = "/{id}";

    public static final String updateUserByIdPutMapping = "/{id}";

  }

}
