package ca.bc.gov.backendstartapi.exception;

/** This class represents a user that already exists and will trigger a RuntimeException. */
public class UserExistsException extends RuntimeException {

  /**
   * Retrieves the message to return to the user.
   *
   * @return A string containing the message
   */
  @Override
  public String getMessage() {
    return "User already registered!";
  }
}
