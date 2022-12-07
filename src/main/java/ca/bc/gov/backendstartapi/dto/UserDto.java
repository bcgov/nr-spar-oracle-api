package ca.bc.gov.backendstartapi.dto;

import ca.bc.gov.backendstartapi.response.BaseResponse;
import ca.bc.gov.backendstartapi.util.Empty;
import ca.bc.gov.backendstartapi.util.ObjectUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/** This class represents a User data transition object. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements BaseResponse, Empty {

  @Size(min = 2, max = 20)
  @NotBlank
  private String firstName;

  @Size(min = 2, max = 20)
  @NotBlank
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
    String template = "UserDto{firstName='%s', lastName='%s'}";
    return String.format(template, firstName, lastName);
  }

  @Override
  public boolean isEmpty() {
    return ObjectUtil.isEmptyOrNull(firstName)
        && ObjectUtil.isEmptyOrNull(lastName);
  }
}
