package ca.bc.gov.backendstartapi.util;

import java.util.List;
import java.util.Objects;

/** This class contains utils methods */
public class ObjectUtil {

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

    if (obj instanceof String) {
      String value = obj.toString();
      return value.trim().isEmpty();
    }

    if (obj instanceof Integer) {
      return Integer.parseInt(String.valueOf(obj)) == 0;
    }

    if (obj instanceof List) {
      List value = (List) obj;
      return value.isEmpty();
    }

    // Check if given object implements Comparable<T>
    if (obj instanceof Empty) {
      return true;
    }

    throw new RuntimeException("Type not supported: " + obj.getClass().getName());
  }
}
