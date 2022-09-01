package ca.bc.gov.backendstartapi.endpoint;

import ca.bc.gov.backendstartapi.response.BaseResponse;
import ca.bc.gov.backendstartapi.vo.CheckVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/** This class represents a check endpoint object. */
@Slf4j
@RestController
public class CheckEndpoint {

  @Value("${nrbestapi.version}")
  private String nrbestapiVersion;

  /**
   * Check if the service is up and running.
   *
   * @return a CheckVo object containing a message and a release version
   */
  @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<? extends BaseResponse>> check() {
    log.info("nrbestapiVersion: {}", nrbestapiVersion);
    CheckVo check = CheckVo.builder().message("OK").release(nrbestapiVersion).build();
    return Mono.just(ResponseEntity.status(HttpStatus.OK).body(check));
  }
}
