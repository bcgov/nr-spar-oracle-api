package ca.bc.gov.backendstartapi.exception;

public class UserNotFoundException extends RuntimeException {

  @Override
  public String getMessage() {
    return "User not registered!";
  }
}
