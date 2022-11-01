package ca.bc.gov.backendstartapi.vault;

import ca.bc.gov.backendstartapi.util.ObjectUtil;
import java.net.URI;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.Versioned;

/** Handle vault access. */
@Service
@Slf4j
public final class VaultAccess {

  @Value("${hashicorp.vault.uri}")
  private String vaultUri;

  private VaultTemplate startTemplate(String token) {
    log.info("Starting Vault Template...");
    log.info("vaultUri: {}", vaultUri);
    log.info("token: {}", token);

    VaultEndpoint vaultEndpoint = VaultEndpoint.from(URI.create(vaultUri));

    // Authentication
    return new VaultTemplate(vaultEndpoint, new TokenAuthentication(token));
  }

  /**
   * Get a secret given a {VaultRequest} request.
   *
   * @param vaultRequest a request containing path and name to be requested.
   * @return a String if found, an empty string otherwise.
   */
  public String getSecret(VaultRequest vaultRequest) {
    log.info(
        "Retrieving secret for path: {} and name: {}",
        vaultRequest.getPath(),
        vaultRequest.getVaultName());
    VaultTemplate vaultTemplate = startTemplate(vaultRequest.getToken());

    log.info("Vault Template is here! Trying to get access!");

    Versioned<Map<String, Object>> secret =
        vaultTemplate
            .opsForVersionedKeyValue(vaultRequest.getPath())
            .get(vaultRequest.getVaultName());

    StringBuilder secretValue = new StringBuilder();
    if (!ObjectUtil.isEmptyOrNull(secret)) {
      secretValue.append("one: ");
      secretValue.append(secret.getData().getOrDefault("password", ""));
      log.info("Secret value (password): {}", secretValue);

      secretValue.append(", two: ");
      secretValue.append(secret.getData().getOrDefault("db_username", ""));
      log.info("Secret value 2 (db_username): {}", secretValue);

      secretValue.append(", three: ");
      secretValue.append(secret.getData().getOrDefault("db_password", ""));
      log.info("Secret value 3 (db_password): {}", secretValue);
    }

    return secretValue.toString();
  }
}
