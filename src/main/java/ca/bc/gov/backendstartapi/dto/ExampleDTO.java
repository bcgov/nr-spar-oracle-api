package ca.bc.gov.backendstartapi.dto;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExampleDTO {

  private Long id;
  private String firstName;
  private String lastName;

  public ExampleDTO() {
    this(0L, "", "");
  }

  public ExampleDTO(Long id, String firstName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

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
    ExampleDTO that = (ExampleDTO) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
