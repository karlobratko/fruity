package hr.algebra.fruity.dto.response;

import java.time.LocalDateTime;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

public record ApiResponse<T>(LocalDateTime timestamp, Boolean success, Integer status, String message, T data) {

  public static <T> ApiResponse<T> of(Boolean success, Integer status, String message, T data) {
    return new ApiResponse<>(LocalDateTime.now(), success, status, message, data);
  }

  public static <T> ApiResponse<T> ok(T data, String message) {
    return of(true, HttpStatus.OK.value(), message, data);
  }

  public static <T> ApiResponse<T> ok(String message) {
    return ok(null, message);
  }

  public static <T> ApiResponse<T> created(T data, String message) {
    return of(true, HttpStatus.CREATED.value(), message, data);
  }

  public static <T> ApiResponse<T> created(String message) {
    return created(null, message);
  }

  public static ApiResponse<?> error(Integer status, String message) {
    return of(false, status, message, null);
  }

  public static ApiResponse<?> error(HttpStatus httpStatus, String message) {
    return error(httpStatus.value(), message);
  }

}
