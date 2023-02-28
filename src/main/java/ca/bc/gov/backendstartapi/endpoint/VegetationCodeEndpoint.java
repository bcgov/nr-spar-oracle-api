package ca.bc.gov.backendstartapi.endpoint;

import ca.bc.gov.backendstartapi.endpoint.parameters.PaginatedViaQuery;
import ca.bc.gov.backendstartapi.endpoint.parameters.PaginationParameters;
import ca.bc.gov.backendstartapi.entity.VegetationCode;
import ca.bc.gov.backendstartapi.repository.VegetationCodeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
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
@Tag(name = "vegetationCode", description = "Codes describing various vegetation species")
public class VegetationCodeEndpoint {

  private final VegetationCodeRepository vegetationCodeRepository;

  VegetationCodeEndpoint(VegetationCodeRepository vegetationCodeRepository) {
    this.vegetationCodeRepository = vegetationCodeRepository;
  }

  /**
   * Fetch information about a single vegetation code.
   *
   * @param code the vegetation code being sought after
   * @return information about the vegetation code {@code code}, if it exists
   * @throws ResponseStatusException with status code 404 if such code doesn't exist
   */
  @GetMapping(path = "/{code}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  @Operation(
      summary = "Fetch a vegetation code by its identifier",
      description = "Returns the vegetation code identified by `code`, if there is one.",
      responses = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
      })
  public VegetationCode findByCode(
      @PathVariable("code")
          @Parameter(
              name = "code",
              in = ParameterIn.PATH,
              description = "Identifier of the vegetation code being sought.")
          String code) {
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
   * @return a list of {@code perPage} or less vegetation codes matching {@code search}, ordered by
   *     identifier
   */
  @GetMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  @Operation(
      summary = "Search for valid vegetation codes by their identifier or description",
      description =
          """
              Search for valid vegetation codes (ones which `effectiveDate` ≤ today < `expiryDate`)
              with identifier or description matching `search`.""",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description =
                "An array with the vegetation codes found, ordered by their identifiers."),
        @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true)))
      })
  @PaginatedViaQuery
  public List<VegetationCode> findEffectiveByCodeOrDescription(
      @RequestParam(name = "search", defaultValue = "")
          @Parameter(
              description =
                  """
                      A string to be matched against the codes' identifier or description. Not
                      providing a value matches everything.""")
          String search,
      @Valid PaginationParameters paginationParameters) {
    return vegetationCodeRepository.findValidByCodeOrDescription(
        search, paginationParameters.skip(), paginationParameters.perPage());
  }
}
