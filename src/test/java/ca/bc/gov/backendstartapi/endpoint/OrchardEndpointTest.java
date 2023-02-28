package ca.bc.gov.backendstartapi.endpoint;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.bc.gov.backendstartapi.entity.Orchard;
import ca.bc.gov.backendstartapi.repository.OrchardRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrchardEndpoint.class)
class OrchardEndpointTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private OrchardRepository orchardRepository;

  @Test
  @DisplayName("findByIdPrdSuccessTest")
  @WithMockUser(roles = "user_read")
  void findByIdPrdSuccessTest() throws Exception {
    Orchard orchardPrd = new Orchard();
    orchardPrd.setId("337");
    orchardPrd.setName("GRANDVIEW");
    orchardPrd.setVegetationCode("PLI");
    orchardPrd.setLotTypeCode('S');
    orchardPrd.setLotTypeDescription("Seed Lot");
    orchardPrd.setStageCode("PRD");

    when(orchardRepository.findById(any())).thenReturn(Optional.of(orchardPrd));

    mockMvc
        .perform(
            get("/api/orchards/{id}", "337")
                .with(csrf().asHeader())
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("337"))
        .andExpect(jsonPath("$.name").value("GRANDVIEW"))
        .andExpect(jsonPath("$.vegetationCode").value("PLI"))
        .andExpect(jsonPath("$.lotTypeCode").value("S"))
        .andExpect(jsonPath("$.lotTypeDescription").value("Seed Lot"))
        .andExpect(jsonPath("$.stageCode").value("PRD"))
        .andReturn();
  }

  @Test
  @DisplayName("findByIdNotFoundTest")
  void findByIdNotFoundTest() {
    //
  }
}
