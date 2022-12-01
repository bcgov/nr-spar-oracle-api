package ca.bc.gov.backendstartapi.endpoint;

import ca.bc.gov.backendstartapi.exception.UserExistsException;
import ca.bc.gov.backendstartapi.exception.UserNotFoundException;
import ca.bc.gov.backendstartapi.response.ExceptionResponse;
import ca.bc.gov.backendstartapi.response.ValidationExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** This class is responsible for handling all kind of exceptions and validations. */
@RestControllerAdvice
public class RestExceptionEndpoint {

  /**
   * Handle all javax.validation exceptions.
   *
   * @param ex MethodArgumentNotValidException instance
   * @return a Map of String containing all the invalid fields and messages
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ValidationExceptionResponse> validationException(
      MethodArgumentNotValidException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ValidationExceptionResponse(ex.getFieldErrors()));
  }

  /**
   * Handle a user existing exception.
   *
   * @param ex UserExistsException instance
   * @return a JSON message
   */
  @ExceptionHandler(UserExistsException.class)
  ResponseEntity<ExceptionResponse> userExists(UserExistsException ex) {
    ExceptionResponse exResponse = new ExceptionResponse(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exResponse);
  }

  /**
   * Handle a user not found exception.
   *
   * @param ex UserNotFoundException instance
   * @return a JSON message
   */
  @ExceptionHandler(UserNotFoundException.class)
  ResponseEntity<ExceptionResponse> userNotFound(UserNotFoundException ex) {
    ExceptionResponse exResponse = new ExceptionResponse(ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exResponse);
  }
}
