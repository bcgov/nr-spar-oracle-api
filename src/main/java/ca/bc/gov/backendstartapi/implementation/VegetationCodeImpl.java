package ca.bc.gov.backendstartapi.implementation;

import ca.bc.gov.backendstartapi.implementation.entity.VegetationCodeEntity;
import ca.bc.gov.backendstartapi.model.VegetationCode;
import jakarta.annotation.Nonnull;
import java.beans.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** Pretty much an immutable {@link VegetationCodeEntity}. */
public record VegetationCodeImpl(
    @Nonnull String code,
    @Nonnull String description,
    @Nonnull LocalDate effectiveDate,
    @Nonnull LocalDate expiryDate)
    implements VegetationCode {

  private static final String NULL_ARGUMENT_ERROR_MESSAGE =
      VegetationCodeImpl.class.getSimpleName() + ".%s cannot be null.";

  /**
   * Builds an instance of this class, checking if the arguments are not null.
   *
   * @throws IllegalArgumentException if any of the arguments are null
   */
  public VegetationCodeImpl {
    if (code == null) {
      throw new IllegalArgumentException(String.format(NULL_ARGUMENT_ERROR_MESSAGE, "code"));
    }
    if (description == null) {
      throw new IllegalArgumentException(String.format(NULL_ARGUMENT_ERROR_MESSAGE, "description"));
    }
    if (effectiveDate == null) {
      throw new IllegalArgumentException(
          String.format(NULL_ARGUMENT_ERROR_MESSAGE, "effectiveDate"));
    }
    if (expiryDate == null) {
      throw new IllegalArgumentException(String.format(NULL_ARGUMENT_ERROR_MESSAGE, "expiryDate"));
    }
    if (!expiryDate.isAfter(effectiveDate)) {
      throw new IllegalArgumentException("expiryDate must be posterior to effectiveDate");
    }
  }

  /**
   * Create an instance of this class from the information of a {@link VegetationCodeEntity}.
   *
   * @param entity the entity from which an instance should be created
   * @return an instance of {@link VegetationCodeImpl} created from the information of {@code
   *     entity}.
   */
  public static VegetationCodeImpl of(VegetationCodeEntity entity) {
    return new VegetationCodeImpl(
        entity.getId(), entity.getDescription(), entity.getEffectiveDate(), entity.getExpiryDate());
  }

  @Override
  @Transient
  public boolean isValid() {
    var now = LocalDateTime.now();
    return now.isAfter(effectiveDate.atStartOfDay()) && now.isBefore(expiryDate.atStartOfDay());
  }
}
