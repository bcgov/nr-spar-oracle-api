package ca.bc.gov.backendstartapi.response;

import java.util.List;
import lombok.Getter;
import org.springframework.validation.FieldError;

/**
 * Used as error responses for REST endpoints when validation constraints (from {@link
 * javax.validation.constraints}) problems occur.
 *
 * <p>This class is to be constructed from {@link FieldError FieldErrors}.
 */
@Getter
public class ValidationExceptionResponse {

  private static final String MESSAGE_TEMPLATE = "%d field(s) with validation problems!";

  private final String errorMessage;
  private final List<FieldIssue> fields;

  /**
   * The sole constructor of this class.
   *
   * @param errors all the validation problems to be listed as a response
   */
  public ValidationExceptionResponse(List<FieldError> errors) {
    this.fields =
        errors.stream()
            .map(error -> new FieldIssue(error.getField(), error.getDefaultMessage()))
            .toList();
    this.errorMessage = String.format(MESSAGE_TEMPLATE, fields.size());
  }
}
