package ca.bc.gov.backendstartapi.util;

import ca.bc.gov.backendstartapi.exception.EmptyObjectNotSupportedException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Objects;

/** This class contains utils methods. */
public class ObjectUtil {

  private ObjectUtil() {}

  /**
   * Check if a given object instance is null or empty.
   *
   * @param obj Object instance to be checked
   * @return true if empty or null, false otherwise
   */
  public static boolean isEmptyOrNull(Object obj) {
    if (Objects.isNull(obj)) {
      return true;
    }

    if (obj instanceof String string) {
      return string.trim().isEmpty();
    }

    if (obj instanceof Integer) {
      return Integer.parseInt(String.valueOf(obj)) == 0;
    }

    if (obj.getClass().isArray()) {
      return Array.getLength(obj) == 0;
    }

    if (Collection.class.isAssignableFrom(obj.getClass())) {
      return Collection.class.cast(obj).isEmpty();
    }

    // Check if given object implements Comparable<T>
    if (obj instanceof Empty empty) {
      return empty.isEmpty();
    }

    throw new EmptyObjectNotSupportedException("Type not supported: " + obj.getClass().getName());
  }
}
