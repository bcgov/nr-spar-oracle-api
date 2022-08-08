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
}
