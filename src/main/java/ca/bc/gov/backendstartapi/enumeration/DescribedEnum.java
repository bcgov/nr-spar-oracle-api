package ca.bc.gov.backendstartapi.enumeration;

/** Implement this in {@link Enum Enums} with values that aren't satisfactorily self-explanatory. */
public interface DescribedEnum {

  /**
   * Get the description this enum value: it may be a single word, as long as it guarantees people
   * can make sense of it.
   *
   * @return the description of this enum value
   */
  String description();
}
