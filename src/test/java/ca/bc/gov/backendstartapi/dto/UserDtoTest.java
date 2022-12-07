package ca.bc.gov.backendstartapi.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserDtoTest {

  @Test
  @DisplayName("Getter and Setter tests")
  void gettersAndSettersTest() {
    UserDto user = new UserDto("Ricardo", "Campos");

    Assertions.assertEquals("Ricardo", user.getFirstName());
    Assertions.assertEquals("Campos", user.getLastName());
  }

  @Test
  @DisplayName("Equals and Hashcode tests")
  void equalsTests() {
    UserDto userA = new UserDto("Ricardo", "Campos");
    UserDto userB = new UserDto("Igor", "Melo");

    Assertions.assertNotEquals(userA, userB);
    Assertions.assertEquals(userA, new UserDto("Ricardo", "Campos"));

    Assertions.assertEquals(518622622, userA.hashCode());
    Assertions.assertEquals(72991099, userB.hashCode());
  }

  @Test
  @DisplayName("ToString test")
  void toStringTests() {
    UserDto userA = new UserDto("Ricardo", "Campos");
    UserDto userB = new UserDto("Igor", "Melo");

    Assertions.assertEquals("UserDto{firstName='Ricardo', lastName='Campos'}", userA.toString());
    Assertions.assertEquals("UserDto{firstName='Igor', lastName='Melo'}", userB.toString());
  }

  @Test
  @DisplayName("IsEmpty test")
  void isEmptyTests() {
    UserDto userA = new UserDto("Ricardo", "Campos");
    UserDto userB = new UserDto("Igor", null);
    UserDto userC = new UserDto();
    UserDto userD = new UserDto("", "");
    UserDto userE = new UserDto(null, null);

    Assertions.assertFalse(userA.isEmpty());
    Assertions.assertFalse(userB.isEmpty());
    Assertions.assertTrue(userC.isEmpty());
    Assertions.assertTrue(userD.isEmpty());
    Assertions.assertTrue(userE.isEmpty());
  }
}
