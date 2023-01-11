package ca.bc.gov.backendstartapi.endpoint;

import ca.bc.gov.backendstartapi.endpoint.parameters.PaginationParameters;
import ca.bc.gov.backendstartapi.entity.VegetationCode;
import ca.bc.gov.backendstartapi.repository.VegetationCodeRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Used for providing information related to {@link VegetationCode vegetation codes} via HTTP
 * requests.
 */
@RestController
@RequestMapping(path = "/vegetationCode")
@Validated
public class VegetationCodeEndpoint {

  @Autowired private VegetationCodeRepository vegetationCodeRepository;

  /**
   * Fetch information about a single vegetation code.
   *
   * @param code the vegetation code being sought after
   * @return information about the vegetation code {@code code}, if it exists; nothing, otherwise
   */
  @GetMapping(path = "/{code}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public ResponseEntity<VegetationCode> findByCode(@PathVariable("code") String code) {
    var retrievalResult = vegetationCodeRepository.findByCode(code);
    return ResponseEntity.of(retrievalResult);
  }

  /**
   * Paginated search for valid vegetation codes with identifiers or descriptions that match the
   * input. Results are ordered by identifier.
   *
   * @param search the string to match the vegetation codes with
   * @param paginationParameters parameters for the pagination of the search results; see {@link
   *     PaginationParameters}
   * @return a list of {@code pageSize} or less vegetation codes, ordered by identifier
   */
  @GetMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<VegetationCode>> findEffectiveByCodeOrDescription(
      @RequestParam(name = "search", defaultValue = "") String search,
      @Valid PaginationParameters paginationParameters) {
    var searchResults =
        vegetationCodeRepository.findValidByCodeOrDescription(
            search, paginationParameters.page(), paginationParameters.pageSize());
    return ResponseEntity.ok(searchResults);
  }
}
