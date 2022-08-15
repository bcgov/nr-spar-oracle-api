package ca.bc.gov.backendstartapi.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.backendstartapi.response.BaseResponse;
import ca.bc.gov.backendstartapi.vo.CheckVO;
import reactor.core.publisher.Mono;

@RestController
public class CheckEndpoint {

  @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<? extends BaseResponse>> check() {
    CheckVO check = CheckVO.builder().message("OK").build();
    return Mono.just(ResponseEntity.status(HttpStatus.OK).body(check));
  }
}
