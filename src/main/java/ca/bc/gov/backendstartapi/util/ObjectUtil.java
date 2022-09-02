package ca.bc.gov.backendstartapi.util;

import java.util.List;
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

    if (obj instanceof List list) {
      return list.isEmpty();
    }

    // Check if given object implements Comparable<T>
    if (obj instanceof Empty empty) {
      return empty.isEmpty();
    }

    throw new RuntimeException("Type not supported: " + obj.getClass().getName());
  }
}
