package ca.bc.gov.backendstartapi.endpoint;

import ca.bc.gov.backendstartapi.entity.Orchard;
import ca.bc.gov.backendstartapi.repository.OrchardRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/** This class contains resources to handle Orchards. */
@Setter
@RestController
@RequestMapping("/api/orchards")
@NoArgsConstructor
@Tag(
    name = "orchard",
    description = "A location where class A seed or class A cuttings are produced.")
public class OrchardEndpoint {

  private OrchardRepository orchardRepository;

  @Autowired
  public OrchardEndpoint(OrchardRepository orchardRepository) {
    this.orchardRepository = orchardRepository;
  }

  /**
   * Gets an Orchard by its code.
   *
   * @param id Identification of the Orchard.
   * @return A {@link Orchard} if found, 404 otherwise
   * @throws ResponseStatusException if the Orchard doesn't exist
   */
  @GetMapping(path = "/{id}", produces = "application/json")
  @PreAuthorize("hasRole('user_read')")
  @Operation(
      summary = "Fetch an orchard by its identifier",
      description = "Returns the orchard identified by `id`, if there is one.",
      responses = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(
            responseCode = "401",
            description = "Access token is missing or invalid",
            content = @Content(schema = @Schema(implementation = Void.class))),
        @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
      })
  public Orchard getOrchardById(
      @PathVariable
          @Parameter(name = "id", in = ParameterIn.PATH, description = "Identifier of the orchard.")
          String id) {
    Optional<Orchard> orchard = orchardRepository.findById(id);

    return orchard.orElseThrow(
        () ->
            new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Orchard %s not found.", id)));
  }
}
