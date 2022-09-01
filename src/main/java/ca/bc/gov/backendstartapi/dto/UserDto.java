package ca.bc.gov.backendstartapi.dto;

import ca.bc.gov.backendstartapi.response.BaseResponse;
import ca.bc.gov.backendstartapi.util.Empty;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/** This class represents a User data transition object. */
@Builder
@Getter
@Setter
public class UserDto implements BaseResponse, Empty {

  @Size(min = 2, max = 20)
  @NotNull
  private String firstName;

  @Size(min = 2, max = 20)
  @NotNull
  private String lastName;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDto userDto = (UserDto) o;
    return firstName.equals(userDto.firstName) && lastName.equals(userDto.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName);
  }

  @Override
  public String toString() {
    return "UserDto{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + '}';
  }

  @Override
  public boolean isEmpty() {
    return hashCode() == 961;
  }
}
