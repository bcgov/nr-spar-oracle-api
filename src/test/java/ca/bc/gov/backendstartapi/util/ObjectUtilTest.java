package ca.bc.gov.backendstartapi.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.bc.gov.backendstartapi.dto.ExampleDto;
import ca.bc.gov.backendstartapi.dto.UserDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ObjectUtilTest {

  @Test
  @DisplayName("IsEmptyOrNull method test")
  void isEmptyOrNullTest() {
    assertTrue(ObjectUtil.isEmptyOrNull(null));

    // String
    assertTrue(ObjectUtil.isEmptyOrNull(""));
    assertTrue(ObjectUtil.isEmptyOrNull("   "));
    assertFalse(ObjectUtil.isEmptyOrNull("  a "));

    // Integer
    Integer value1 = 0;
    Integer value2 = 1;
    assertTrue(ObjectUtil.isEmptyOrNull(value1));
    assertFalse(ObjectUtil.isEmptyOrNull(value2));

    // Array
    Integer[] array1 = new Integer[] {1, 2, 3};
    Integer[] array2 = new Integer[] {};
    assertFalse(ObjectUtil.isEmptyOrNull(array1));
    assertTrue(ObjectUtil.isEmptyOrNull(array2));

    // List
    List<Object> emptyList = new ArrayList<>();
    List<Object> nonEmptyList = Arrays.asList("1", "2");
    assertTrue(ObjectUtil.isEmptyOrNull(emptyList));
    assertFalse(ObjectUtil.isEmptyOrNull(nonEmptyList));
  }

  @Test
  @DisplayName("IsEmptyOrNull supported classes")
  void isEmptyOrNullTest_emptyInterface() {
    assertTrue(ObjectUtil.isEmptyOrNull(new UserDto()));
  }

  @Test
  @DisplayName("IsEmptyOrNull class not supported")
  void isEmptyOrNullTest_exception() {
    final ExampleDto example = new ExampleDto();
    assertThrows(RuntimeException.class, () -> ObjectUtil.isEmptyOrNull(example));
  }
}
