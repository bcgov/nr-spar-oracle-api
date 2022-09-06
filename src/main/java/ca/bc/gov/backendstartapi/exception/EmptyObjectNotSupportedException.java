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
}
