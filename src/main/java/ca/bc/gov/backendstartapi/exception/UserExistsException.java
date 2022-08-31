package ca.bc.gov.backendstartapi.exception;

public class UserExistsException extends RuntimeException {

  @Override
  public String getMessage() {
    return "User already registered!";
  }
}
