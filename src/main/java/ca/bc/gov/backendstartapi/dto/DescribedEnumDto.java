package ca.bc.gov.backendstartapi.dto;

import ca.bc.gov.backendstartapi.enumeration.DescribedEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

/**
 * A DTO for {@link DescribedEnum DescribedEnums}.
 *
 * @param <E> an {@link Enum} class with values that need description
 */
@Getter
@ToString
@Schema(description = "An enum with its code and description.")
public final class DescribedEnumDto<E extends Enum<E> & DescribedEnum> {

  /** The enum itself. */
  @Schema(description = "The code itself.")
  private final E code;

  /** A description for the enum in {@code code}. */
  @Schema(description = "The code's meaning.")
  private final String description;

  /**
   * Build an instance from a {@link DescribedEnum}.
   *
   * @param enumElement an enum element with a description
   */
  public DescribedEnumDto(E enumElement) {
    code = enumElement;
    this.description = enumElement.description();
  }
}
