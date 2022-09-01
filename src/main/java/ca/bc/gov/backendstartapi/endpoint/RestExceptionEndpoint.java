package ca.bc.gov.backendstartapi.endpoint;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import ca.bc.gov.backendstartapi.exception.UserExistsException;
import ca.bc.gov.backendstartapi.exception.UserNotFoundException;

/** This class is responsible for handling all kind of exceptions and validations. */
@RestControllerAdvice
public class RestExceptionEndpoint {

  /**
   * Handle all javax.validation exceptions.
   *
   * @param ex WebExchangeBindException instance
   * @return a Map of String containing all the invalid fields and messages
   */
  @ExceptionHandler(WebExchangeBindException.class)
  ResponseEntity<Map<String, String>> generalException(WebExchangeBindException ex) {
    final Map<String, String> errorMap = new HashMap<>();
    ex.getFieldErrors().forEach(f -> errorMap.put(f.getField(), f.getDefaultMessage()));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
  }

  /**
   * Handle a user existing exception.
   *
   * @param ex UserExistsException instance
   * @return a JSON message
   */
  @ExceptionHandler(UserExistsException.class)
  ResponseEntity<Map<String, String>> userExists(UserExistsException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createMessageMap(ex.getMessage()));
  }

  /**
   * Handle a user not found exception.
   *
   * @param ex UserNotFoundException instance
   * @return a JSON message
   */
  @ExceptionHandler(UserNotFoundException.class)
  ResponseEntity<Map<String, String>> userNotFound(UserNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createMessageMap(ex.getMessage()));
  }

  /**
   * Create a Map of String with a single message key and its content
   *
   * @param message The message that should be returned to the user
   * @return A Map of String with the message
   */
  private Map<String, String> createMessageMap(String message) {
    final Map<String, String> errorMap = new HashMap<>();
    errorMap.put("message", message);
    return errorMap;
  }
}
