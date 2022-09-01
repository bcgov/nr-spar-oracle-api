package ca.bc.gov.backendstartapi.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ca.bc.gov.backendstartapi.dto.ExampleDto;
import ca.bc.gov.backendstartapi.dto.UserDto;

public class ObjectUtilTest {

  @Test
  @DisplayName("IsEmptyOrNull method test")
  void isEmptyOrNullTest() {
    Assertions.assertTrue(ObjectUtil.isEmptyOrNull(null));

    // String
    Assertions.assertTrue(ObjectUtil.isEmptyOrNull(""));
    Assertions.assertTrue(ObjectUtil.isEmptyOrNull("   "));
    Assertions.assertFalse(ObjectUtil.isEmptyOrNull("  a "));

    // Integer
    Integer value1 = 0;
    Integer value2 = 1;
    Assertions.assertTrue(ObjectUtil.isEmptyOrNull(value1));
    Assertions.assertFalse(ObjectUtil.isEmptyOrNull(value2));

    // List
    List<Object> emptyList = new ArrayList<>();
    List<Object> nonEmptyList = Arrays.asList("1", "2");
    Assertions.assertTrue(ObjectUtil.isEmptyOrNull(emptyList));
    Assertions.assertFalse(ObjectUtil.isEmptyOrNull(nonEmptyList));
  }

  @Test
  @DisplayName("IsEmptyOrNull supported classes")
  void isEmptyOrNullTest_emptyInterface() {
    Assertions.assertTrue(ObjectUtil.isEmptyOrNull(UserDto.builder().build()));
  }

  @Test
  @DisplayName("IsEmptyOrNull class not supported")
  void isEmptyOrNullTest_exception() {
    Assertions.assertThrows(RuntimeException.class, () -> {
      Assertions.assertTrue(ObjectUtil.isEmptyOrNull(new ExampleDto()));
    });
  }
}
