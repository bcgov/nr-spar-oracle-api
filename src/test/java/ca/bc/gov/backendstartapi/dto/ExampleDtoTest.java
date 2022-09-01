package ca.bc.gov.backendstartapi.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExampleDtoTest {

  @Test
  void getStringPropsTest() {
    ExampleDto dto = new ExampleDto();
    dto.setId(1L);
    dto.setFirstName("Ricardo");
    dto.setLastName("Campos");

    Assertions.assertEquals(1L, dto.getId());
    Assertions.assertEquals("Ricardo", dto.getFirstName());
    Assertions.assertEquals("Campos", dto.getLastName());
    Assertions.assertEquals(
        "id: 1, firstName: 'Ricardo', lastName: 'Campos'", dto.getStringProps());
  }

  @Test
  void getStringPropsTestTwo() {
    ExampleDto dto = new ExampleDto(2L, "Ricardo", "Campos");

    Assertions.assertEquals(2L, dto.getId());
    Assertions.assertEquals("Ricardo", dto.getFirstName());
    Assertions.assertEquals("Campos", dto.getLastName());
    Assertions.assertEquals(
        "id: 2, firstName: 'Ricardo', lastName: 'Campos'", dto.getStringProps());
  }

  @Test
  void getStringPropsTestThree() {
    ExampleDto dto = new ExampleDto(2L, "Ricardo", "Campos");
    final String dtoString = "ExampleDto(id=2, firstName=Ricardo, lastName=Campos)";
    final int hashCode = 33;

    ExampleDto dtoB = new ExampleDto(3L, "Ricardo", "Campos");
    final boolean bothEquals = dto.equals(dtoB);

    Assertions.assertEquals(dtoString, dto.toString());
    Assertions.assertEquals(hashCode, dto.hashCode());
    Assertions.assertFalse(bothEquals);
  }

  @Test
  void getStringPropsTestFour() {
    ExampleDto dto = new ExampleDto();
    final String dtoString = "ExampleDto(id=0, firstName=, lastName=)";
    final int hashCode = 31;

    ExampleDto dtoB = new ExampleDto();

    Assertions.assertEquals(dtoString, dto.toString());
    Assertions.assertEquals(hashCode, dto.hashCode());
    Assertions.assertEquals(dto.getId(), 0L);
    Assertions.assertEquals(dto.getFirstName(), "");
    Assertions.assertEquals(dto.getLastName(), "");
    Assertions.assertEquals(dto, dto);
    Assertions.assertNotEquals(dto, null);
  }
}
