package ca.bc.gov.backendstartapi.endpoint;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.bc.gov.backendstartapi.entity.FundingSource;
import ca.bc.gov.backendstartapi.repository.FundingSourceRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
@WebMvcTest(FundingSourceEndpoint.class)
class FundingSourceEndpointTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private FundingSourceRepository fundingSourceRepository;

  @Test
  @DisplayName("findAllSuccessTest")
  @WithMockUser(roles = "user_read")
  void findAllSuccessTest() throws Exception {
    FundingSource fundingSourceBct = new FundingSource();
    fundingSourceBct.setCode("BCT");
    fundingSourceBct.setDescription("BC Timber Sales");
    fundingSourceBct.setEffectiveDate(LocalDate.parse("2003-04-01"));
    fundingSourceBct.setExpiryDate(LocalDate.parse("9999-12-31"));

    FundingSource fundingSourceCbi = new FundingSource();
    fundingSourceCbi.setCode("CBI");
    fundingSourceCbi.setDescription("Carbon Offset Investment");
    fundingSourceCbi.setEffectiveDate(LocalDate.parse("2013-08-01"));
    fundingSourceCbi.setExpiryDate(LocalDate.parse("9999-12-31"));

    FundingSource fundingSourceCl = new FundingSource();
    fundingSourceCl.setCode("CL");
    fundingSourceCl.setDescription("Catastrophic Losses");
    fundingSourceCl.setEffectiveDate(LocalDate.parse("1905-01-01"));
    fundingSourceCl.setExpiryDate(LocalDate.parse("2099-09-30"));

    List<FundingSource> sources = new ArrayList<>();
    sources.add(fundingSourceBct);
    sources.add(fundingSourceCbi);
    sources.add(fundingSourceCl);

    when(fundingSourceRepository.findAllValid()).thenReturn(sources);

    mockMvc
        .perform(
            get("/api/funding-sources")
                .with(csrf().asHeader())
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].code").value("BCT"))
        .andExpect(jsonPath("$[0].description").value("BC Timber Sales"))
        .andExpect(jsonPath("$[0].effectiveDate").value("2003-04-01"))
        .andExpect(jsonPath("$[0].expiryDate").value("9999-12-31"))
        .andExpect(jsonPath("$[1].code").value("CBI"))
        .andExpect(jsonPath("$[1].description").value("Carbon Offset Investment"))
        .andExpect(jsonPath("$[1].effectiveDate").value("2013-08-01"))
        .andExpect(jsonPath("$[1].expiryDate").value("9999-12-31"))
        .andExpect(jsonPath("$[2].code").value("CL"))
        .andExpect(jsonPath("$[2].description").value("Catastrophic Losses"))
        .andExpect(jsonPath("$[2].effectiveDate").value("1905-01-01"))
        .andExpect(jsonPath("$[2].expiryDate").value("2099-09-30"))
        .andReturn();
  }

  @Test
  @DisplayName("findAllNoAuthorizedTest")
  void findAllNoAuthorizedTest() throws Exception {
    mockMvc
        .perform(
            get("/api/funding-sources")
                .with(csrf().asHeader())
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(401))
        .andReturn();
  }

}
