package hr.algebra.fruity.dto.response;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public record ErrorApiResponse(
  LocalDateTime timestamp,
  Integer status,
  String error,
  String message,
  String path
) {

  public static ErrorApiResponse of(HttpStatus status, String message, String path) {
    return of(status.value(), status.getReasonPhrase(), message, path);
  }

  public static ErrorApiResponse of(Integer status, String error, String message, String path) {
    return new ErrorApiResponse(LocalDateTime.now(), status, error, message, path);
  }

}
