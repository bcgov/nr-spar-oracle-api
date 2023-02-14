package ca.bc.gov.backendstartapi.dto;

import ca.bc.gov.backendstartapi.enumeration.DescribedEnum;
import lombok.Getter;
import lombok.ToString;

/**
 * A DTO for {@link DescribedEnum DescribedEnums}.
 *
 * @param <E> an {@link Enum} class with values that need description
 */
@Getter
@ToString
public final class DescribedEnumDto<E extends Enum<E> & DescribedEnum> {

  /** The enum itself. */
  private final E code;

  /** A description for the enum in {@code code}. */
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
