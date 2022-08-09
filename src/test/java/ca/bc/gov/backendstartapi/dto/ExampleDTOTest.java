package ca.bc.gov.backendstartapi.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExampleDTOTest {

    @Test
    public void getStringPropsTest() {
        ExampleDTO dto = new ExampleDTO();
        dto.setId(1L);
        dto.setFirstName("Ricardo");
        dto.setLastName("Campos");

        Assertions.assertEquals(1L, dto.getId());
        Assertions.assertEquals("Ricardo", dto.getFirstName());
        Assertions.assertEquals("Campos", dto.getLastName());
        Assertions.assertEquals("id: 1, firstName: 'Ricardo', lastName: 'Campos'", dto.getStringProps());
    }

    @Test
    public void getStringPropsTestTwo() {
        ExampleDTO dto = new ExampleDTO(2L, "Ricardo", "Campos");

        Assertions.assertEquals(2L, dto.getId());
        Assertions.assertEquals("Ricardo", dto.getFirstName());
        Assertions.assertEquals("Campos", dto.getLastName());
        Assertions.assertEquals("id: 2, firstName: 'Ricardo', lastName: 'Campos'", dto.getStringProps());
    }

    @Test
    public void getStringPropsTestThree() {
        ExampleDTO dto = new ExampleDTO(2L, "Ricardo", "Campos");
        final String dtoString = "ExampleDTO(id=2, firstName=Ricardo, lastName=Campos)";
        final int hashCode = 33;

        ExampleDTO dtoB = new ExampleDTO(3L, "Ricardo", "Campos");
        final boolean bothEquals = dto.equals(dtoB);

        Assertions.assertEquals(dtoString, dto.toString());
        Assertions.assertEquals(hashCode, dto.hashCode());
        Assertions.assertFalse(bothEquals);
    }

    @Test
    public void getStringPropsTestFour() {
        ExampleDTO dto = new ExampleDTO();
        final String dtoString = "ExampleDTO(id=0, firstName=, lastName=)";
        final int hashCode = 31;

        ExampleDTO dtoB = new ExampleDTO();

        Assertions.assertEquals(dtoString, dto.toString());
        Assertions.assertEquals(hashCode, dto.hashCode());
        Assertions.assertEquals(dto.getId(), 0L);
        Assertions.assertEquals(dto.getFirstName(), "");
        Assertions.assertEquals(dto.getLastName(), "");
        Assertions.assertEquals(dto, dto);
        Assertions.assertNotEquals(dto, null);
    }
}
