package ca.bc.gov.backendstartapi.dto;

/** This class represents an example data object. */
public record ExampleDto(Long id, String firstName, String lastName) {

  public static ExampleDto empty() {
    return new ExampleDto(0L, "", "");
  }

  /**
   * Create a string with all props.
   *
   * @return a string representing all the properties
   */
  public String getStringProps() {
    StringBuilder builder = new StringBuilder();
    if (id > 0) {
      builder.append("id: ").append(id);
    }
    if (!firstName.isEmpty()) {
      builder.append(", firstName: '").append(firstName).append("'");
    }
    if (!lastName.isEmpty()) {
      builder.append(", lastName: '").append(lastName).append("'");
    }

    return builder.toString();
  }
}
