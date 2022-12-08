package ca.bc.gov.backendstartapi.dto;

import ca.bc.gov.backendstartapi.response.BaseResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** This class represents a User data transition object. */
public record UserDto(
    @Size(min = 2, max = 20) @NotBlank String firstName,
    @Size(min = 2, max = 20) @NotBlank String lastName)
    implements BaseResponse {}
