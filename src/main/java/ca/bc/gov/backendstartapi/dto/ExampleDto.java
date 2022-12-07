package ca.bc.gov.backendstartapi.dto;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** This class represents an example data object. */
@Getter
@Setter
@ToString
public class ExampleDto {

  private Long id;
  private String firstName;
  private String lastName;

  public ExampleDto() {
    this(0L, "", "");
  }

  /**
   * Create a new Example Dto.
   *
   * @param id id to be set
   * @param firstName first name of the example
   * @param lastName last name of the example
   */
  public ExampleDto(Long id, String firstName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExampleDto that = (ExampleDto) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
