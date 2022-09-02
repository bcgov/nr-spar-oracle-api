package ca.bc.gov.backendstartapi.exception;

/** This class represents a not supported class that doesn't implement Empty interface. */
public class EmptyObjectNotSupportedException extends RuntimeException {

  /**
   * Create an exception with a message.
   *
   * @param message the message to be shown
   */
  public EmptyObjectNotSupportedException(String message) {
    super(message);
  }

  /**
   * Retrieves the message to return to the user.
   *
   * @return A string containing the message
   */
  @Override
  public String getMessage() {
    return "Given class doesn't implement Empty interface.";
  }
}
