package ca.bc.gov.backendstartapi.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExampleDtoTest {

  @Test
  void getStringPropsTest() {
    ExampleDto dto = new ExampleDto(1L, "Ricardo", "Campos");

    Assertions.assertEquals(1L, dto.id());
    Assertions.assertEquals("Ricardo", dto.firstName());
    Assertions.assertEquals("Campos", dto.lastName());
    Assertions.assertEquals(
        "id: 1, firstName: 'Ricardo', lastName: 'Campos'", dto.getStringProps());
  }

  @Test
  void getStringPropsTestTwo() {
    ExampleDto dto = new ExampleDto(2L, "Ricardo", "Campos");

    Assertions.assertEquals(2L, dto.id());
    Assertions.assertEquals("Ricardo", dto.firstName());
    Assertions.assertEquals("Campos", dto.lastName());
    Assertions.assertEquals(
        "id: 2, firstName: 'Ricardo', lastName: 'Campos'", dto.getStringProps());
  }
}
