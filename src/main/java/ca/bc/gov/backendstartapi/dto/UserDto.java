package ca.bc.gov.backendstartapi.dto;

import ca.bc.gov.backendstartapi.response.BaseResponse;
import ca.bc.gov.backendstartapi.util.Empty;
import ca.bc.gov.backendstartapi.util.ObjectUtil;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/** This class represents a User data transition object. */
public record UserDto(
    @Size(min = 2, max = 20) @NotBlank String firstName,
    @Size(min = 2, max = 20) @NotBlank String lastName)
    implements BaseResponse, Empty {

  @Override
  public boolean isEmpty() {
    return ObjectUtil.isEmptyOrNull(firstName) && ObjectUtil.isEmptyOrNull(lastName);
  }
}
