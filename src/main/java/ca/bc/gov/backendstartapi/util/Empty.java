package ca.bc.gov.backendstartapi.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

/** This interface should be used to identify an empty object. */
public interface Empty {

  /**
   * Check if a given object is empty.
   *
   * @return true if empty (all fields are empty), false otherwise
   */
  @JsonIgnore
  boolean isEmpty();
}
