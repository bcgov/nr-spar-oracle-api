package ca.bc.gov.backendstartapi.exception;

/** This class represents a user that already exists and will trigger a RuntimeException. */
public class UserExistsException extends RuntimeException {

  public UserExistsException() {
    super("User already registered!");
  }
}
