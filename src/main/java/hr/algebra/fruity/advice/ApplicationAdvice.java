package hr.algebra.fruity.advice;

import hr.algebra.fruity.dto.response.ErrorApiResponse;
import hr.algebra.fruity.exception.BadRequestException;
import hr.algebra.fruity.exception.ConflictException;
import hr.algebra.fruity.exception.NotFoundException;
import hr.algebra.fruity.exception.RegistrationTokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApplicationAdvice {

  @ExceptionHandler({
    IllegalArgumentException.class,
    NoSuchElementException.class,
    BadCredentialsException.class,
    DisabledException.class,
    LockedException.class,
    HttpMessageNotReadableException.class,
    BadRequestException.class
  })
  public ResponseEntity<ErrorApiResponse> handleBadRequestException(HttpServletRequest request, RuntimeException e) {
    log.debug(e.getMessage());
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(
        ErrorApiResponse.of(
          HttpStatus.BAD_REQUEST,
          e.getMessage(),
          request.getRequestURL().toString()
        )
      );
  }

  @ExceptionHandler({
    RegistrationTokenExpiredException.class,
    ConflictException.class
  })
  public ResponseEntity<ErrorApiResponse> handleConflictException(HttpServletRequest request, RuntimeException e) {
    log.debug(e.getMessage());
    return ResponseEntity
      .status(HttpStatus.CONFLICT)
      .body(
        ErrorApiResponse.of(
          HttpStatus.CONFLICT,
          e.getMessage(),
          request.getRequestURL().toString()
        )
      );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorApiResponse> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
    val errors = e.getBindingResult().getAllErrors().stream()
      .map(DefaultMessageSourceResolvable::getDefaultMessage)
      .collect(Collectors.joining("\n"));

    log.debug(errors);
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(
        ErrorApiResponse.of(
          HttpStatus.BAD_REQUEST,
          errors,
          request.getRequestURL().toString()
        )
      );
  }

  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<ErrorApiResponse> handleNotFoundException(HttpServletRequest request, NotFoundException e) {
    log.debug(e.getMessage());
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(
        ErrorApiResponse.of(
          HttpStatus.NOT_FOUND,
          e.getMessage(),
          request.getRequestURL().toString()
        )
      );
  }

  @ExceptionHandler({
    IllegalStateException.class,
    Exception.class
  })
  public ResponseEntity<ErrorApiResponse> handleException(HttpServletRequest request, Exception e) {
    log.error(e.getMessage());
    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(
        ErrorApiResponse.of(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Interna greška na serveru, molimo Vas da kontaktirate administratora.",
          request.getRequestURL().toString()
        )
      );
  }

}
