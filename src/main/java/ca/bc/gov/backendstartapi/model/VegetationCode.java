package ca.bc.gov.backendstartapi.model;

import jakarta.annotation.Nonnull;

/** Used to identify vegetation species. Has a period of validity. */
public interface VegetationCode {

  /** The code itself. Works as an identifier. */
  @Nonnull
  String code();

  /** A description of what is associated with this code; usually a list of families of plants. */
  @Nonnull
  String description();

  /** If the code is valid at this moment. */
  boolean isValid();
}
