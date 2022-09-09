package ca.bc.gov.backendstartapi.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/** This class represents any kind of exception that this API may go through. */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class ExceptionResponse {

  private static final String MESSAGE_TEMPLATE = "%d field(s) with validation problem!";
  private String errorMessage;
  private List<FieldExceptionResponse> fields;

  public ExceptionResponse() {
    this(1);
  }

  public ExceptionResponse(int issuesCount) {
    this(String.format(MESSAGE_TEMPLATE, issuesCount));
  }

  public ExceptionResponse(String errorMessage) {
    this.errorMessage = errorMessage;
    this.fields = new ArrayList<>();
  }
}
