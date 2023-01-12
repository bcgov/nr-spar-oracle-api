package ca.bc.gov.backendstartapi.endpoint;

import ca.bc.gov.backendstartapi.endpoint.parameters.PaginationParameters;
import ca.bc.gov.backendstartapi.entity.VegetationCode;
import ca.bc.gov.backendstartapi.repository.VegetationCodeRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Used for providing information related to {@link VegetationCode vegetation codes} via HTTP
 * requests.
 */
@RestController
@RequestMapping(path = "/api/vegetation-codes")
@Validated
public class VegetationCodeEndpoint {

  @Autowired private VegetationCodeRepository vegetationCodeRepository;

  /**
   * Fetch information about a single vegetation code.
   *
   * @param code the vegetation code being sought after
   * @return information about the vegetation code {@code code}, if it exists
   * @throws ResponseStatusException with status code 404 if such code doesn't exist
   */
  @GetMapping(path = "/{code}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public VegetationCode findByCode(@PathVariable("code") String code) {

    var retrievalResult = vegetationCodeRepository.findByCode(code);
    return retrievalResult.orElseThrow(
        () ->
            new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Vegetation code %s not found.", code)));
  }

  /**
   * Paginated search for valid vegetation codes with identifiers or descriptions that match the
   * input. Results are ordered by identifier.
   *
   * @param search the string to match the vegetation codes with
   * @param paginationParameters parameters for the pagination of the search results; see {@link
   *     PaginationParameters}
   * @return a list of {@code size} or less vegetation codes, ordered by identifier
   */
  @GetMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public List<VegetationCode> findEffectiveByCodeOrDescription(
      @RequestParam(name = "search", defaultValue = "") String search,
      @Valid PaginationParameters paginationParameters) {
    return vegetationCodeRepository.findValidByCodeOrDescription(
        search, paginationParameters.skip(), paginationParameters.size());
  }
}
