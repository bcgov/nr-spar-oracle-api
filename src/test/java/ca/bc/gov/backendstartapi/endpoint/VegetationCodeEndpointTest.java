package ca.bc.gov.backendstartapi.endpoint;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.bc.gov.backendstartapi.entity.VegetationCode;
import ca.bc.gov.backendstartapi.implementation.VegetationCodeRepositoryImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class VegetationCodeEndpointTest {

  private MockMvc mockMvc;

  @MockBean private VegetationCodeRepositoryImpl vegetationCodeRepository;

  @Autowired private WebApplicationContext webApplicationContext;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  void fetchExistentCode() throws Exception {
    VegetationCode vc =
        new VegetationCode(
            "C1",
            "Code 1",
            LocalDate.of(2020, 1, 1),
            LocalDate.of(2025, 1, 1),
            LocalDateTime.now());

    given(vegetationCodeRepository.findByCode("C1")).willReturn(Optional.of(vc));

    mockMvc
        .perform(get("/vegetationCode/C1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void fetchNonExistentCode() throws Exception {
    VegetationCode vc =
        new VegetationCode(
            "C1",
            "Code 1",
            LocalDate.of(2020, 1, 1),
            LocalDate.of(2025, 1, 1),
            LocalDateTime.now());

    given(vegetationCodeRepository.findByCode("C1")).willReturn(Optional.of(vc));
    given(vegetationCodeRepository.findByCode("C2")).willReturn(Optional.empty());

    mockMvc
        .perform(
            get("/vegetationCode/C2").with(csrf().asHeader()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(""));
  }

  @Test
  void searchWithMatches() throws Exception {
    VegetationCode vc =
        new VegetationCode(
            "C1",
            "Code 1",
            LocalDate.of(2020, 1, 1),
            LocalDate.of(2025, 1, 1),
            LocalDateTime.now());

    given(vegetationCodeRepository.findValidByCodeOrDescription("1", 0, 20))
        .willReturn(List.of(vc));

    mockMvc
        .perform(
            get("/vegetationCode?search=1")
                .with(csrf().asHeader())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void searchWithNoMatch() throws Exception {
    given(vegetationCodeRepository.findValidByCodeOrDescription("1", 0, 20))
        .willReturn(Collections.emptyList());

    mockMvc
        .perform(
            get("/vegetationCode?search=1")
                .with(csrf().asHeader())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void searchWithNegativePage() throws Exception {
    VegetationCode vc =
        new VegetationCode(
            "C1",
            "Code 1",
            LocalDate.of(2020, 1, 1),
            LocalDate.of(2025, 1, 1),
            LocalDateTime.now());

    given(vegetationCodeRepository.findValidByCodeOrDescription("1", -1, 20))
        .willReturn(List.of(vc));

    mockMvc
        .perform(
            get("/vegetationCode?search=1&page=-1")
                .with(csrf().asHeader())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void searchWithNonPositivePageSize() throws Exception {
    VegetationCode vc =
        new VegetationCode(
            "C1",
            "Code 1",
            LocalDate.of(2020, 1, 1),
            LocalDate.of(2025, 1, 1),
            LocalDateTime.now());

    given(vegetationCodeRepository.findValidByCodeOrDescription("1", 0, 0)).willReturn(List.of(vc));

    mockMvc
        .perform(
            get("/vegetationCode?search=1&pageSize=0")
                .with(csrf().asHeader())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}
