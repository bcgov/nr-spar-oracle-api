package ca.bc.gov.backendstartapi.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserDtoTest {

  @Test
  @DisplayName("Getter and Setter tests")
  void gettersAndSettersTest() {
    UserDto user = UserDto.builder().firstName("Ricardo").lastName("Campos").build();

    Assertions.assertEquals("Ricardo", user.getFirstName());
    Assertions.assertEquals("Campos", user.getLastName());
  }

  @Test
  @DisplayName("Equals and Hashcode tests")
  void equalsTests() {
    UserDto userA = UserDto.builder().firstName("Ricardo").lastName("Campos").build();
    UserDto userB = UserDto.builder().firstName("Igor").lastName("Melo").build();

    Assertions.assertNotEquals(userA, userB);
    Assertions.assertEquals(userA, UserDto.builder().firstName("Ricardo").lastName("Campos").build());

    Assertions.assertEquals(518622622, userA.hashCode());
    Assertions.assertEquals(72991099, userB.hashCode());
  }

  @Test
  @DisplayName("ToString test")
  void toStringTests() {
    UserDto userA = UserDto.builder().firstName("Ricardo").lastName("Campos").build();
    UserDto userB = UserDto.builder().firstName("Igor").lastName("Melo").build();

    Assertions.assertEquals("UserDto{firstName='Ricardo', lastName='Campos'}", userA.toString());
    Assertions.assertEquals("UserDto{firstName='Igor', lastName='Melo'}", userB.toString());
  }

  @Test
  @DisplayName("IsEmpty test")
  void isEmptyTests() {
    UserDto userA = UserDto.builder().firstName("Ricardo").lastName("Campos").build();
    UserDto userB = UserDto.builder().firstName("Igor").build();
    UserDto userC = UserDto.builder().build();
    UserDto userD = UserDto.builder().firstName("").lastName("").build();
    UserDto userE = UserDto.builder().firstName(null).lastName(null).build();

    Assertions.assertFalse(userA.isEmpty());
    Assertions.assertFalse(userB.isEmpty());
    Assertions.assertTrue(userC.isEmpty());
    Assertions.assertTrue(userD.isEmpty());
    Assertions.assertTrue(userE.isEmpty());
  }
}
