package ca.bc.gov.backendstartapi.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** This class contains fields and its validations messages. */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldExceptionResponse {

  private String fieldName;
  private String fieldMessage;
}
