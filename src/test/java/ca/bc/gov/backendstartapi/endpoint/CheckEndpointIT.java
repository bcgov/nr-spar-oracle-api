package ca.bc.gov.backendstartapi.endpoint;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import ca.bc.gov.backendstartapi.vo.CheckVo;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@WebFluxTest(CheckEndpoint.class)
class CheckEndpointIT {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  @DisplayName("Check test")
  void checkTest() {
    webTestClient.get()
            .uri("/check")
             .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(CheckVo.class)
        .value(CheckVo::getMessage, equalTo("OK"));

  }
}
