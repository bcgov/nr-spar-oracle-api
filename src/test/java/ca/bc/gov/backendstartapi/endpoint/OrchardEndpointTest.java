package ca.bc.gov.backendstartapi.endpoint;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.bc.gov.backendstartapi.dto.OrchardLotTypeDescriptionDto;
import ca.bc.gov.backendstartapi.service.OrchardService;
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

  @MockBean private OrchardService orchardService;

  @Test
  @DisplayName("findByIdPrdSuccessTest")
  @WithMockUser(roles = "user_read")
  void findByIdPrdSuccessTest() throws Exception {
    OrchardLotTypeDescriptionDto descriptionDto = new OrchardLotTypeDescriptionDto(
        "337",
        "GRANDVIEW",
        "PLI",
        'S',
        "Seed Lot",
        "PRD"
    );

    when(orchardService.findNotRetiredOrchardValidLotType(any()))
        .thenReturn(Optional.of(descriptionDto));

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
  @WithMockUser(roles = "user_read")
  void findByIdNotFoundTest() throws Exception {
    when(orchardService.findNotRetiredOrchardValidLotType(any())).thenReturn(Optional.empty());

    mockMvc
        .perform(
            get("/api/orchards/{id}", "612")
                .with(csrf().asHeader())
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andReturn();
  }
}
