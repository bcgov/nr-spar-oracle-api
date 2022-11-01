package ca.bc.gov.backendstartapi.endpoint;

import ca.bc.gov.backendstartapi.vault.VaultAccess;
import ca.bc.gov.backendstartapi.vault.VaultRequest;
import ca.bc.gov.backendstartapi.vo.CheckVo;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/** This class represents a check endpoint object. */
@Slf4j
@RestController
public class CheckEndpoint {

  @Value("${nrbestapi.version}")
  private String nrbestapiVersion;

  @Autowired
  VaultAccess vaultAccess;

  /**
   * Check if the service is up and running.
   *
   * @return a CheckVo object containing a message and a release version
   */
  @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
  public CheckVo check() {
    log.info("nrbestapiVersion: {}", nrbestapiVersion);
    return CheckVo.builder().message("OK").release(nrbestapiVersion).build();
  }

  @PostMapping(value = "/vault", produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, String> vaultTest(@RequestBody VaultRequest vaultRequest) {
    Map<String, String> vaultMap = new HashMap<>();

    String secretValue = vaultAccess.getSecret(vaultRequest);
    vaultMap.put(vaultRequest.getVaultName(), secretValue);

    return vaultMap;
  }
}
