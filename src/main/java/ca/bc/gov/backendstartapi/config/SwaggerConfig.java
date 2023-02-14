package ca.bc.gov.backendstartapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * General information to be displayed in the documentation of our API, following the <a
 * href="https://spec.openapis.org/oas/latest.html">OpenAPI specification</a>.
 *
 * <p>The generated documentation is to be rendered by <a href="https://swagger.io/">Swagger</a>.
 */
@Configuration
public class SwaggerConfig {

  private static final String BEARER_SECURITY_SCHEME_NAME = "bearerAuth";

  /** General information about our API. */
  @Bean
  public OpenAPI theRestApi() {
    return new OpenAPI()
        .info(
            new Info()
                .title("THE database REST API")
                .description("A REST API to fetch information from the THE database.")
                .version("v0.0.1")
                .termsOfService(
                    "https://www2.gov.bc.ca/gov/content/data/open-data/api-terms-of-use-for-ogl-information")
                .license(
                    new License()
                        .name("OGL-BC")
                        .url(
                            "https://www2.gov.bc.ca/gov/content/data/open-data/open-government-licence-bc")))
        .externalDocs(
            new ExternalDocumentation()
                .description("Our Jira project")
                .url("https://apps.nrs.gov.bc.ca/int/jira/projects/FSADT2"))
        .components(
            new Components()
                .addSecuritySchemes(
                    BEARER_SECURITY_SCHEME_NAME,
                    new SecurityScheme()
                        .name(BEARER_SECURITY_SCHEME_NAME)
                        .type(Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
        .addSecurityItem(new SecurityRequirement().addList(BEARER_SECURITY_SCHEME_NAME))
        // TODO: find a way to keep this closer to the controller itself.
        .paths(
            new Paths()
                .addPathItem(
                    "/api/seedlot-status",
                    new PathItem()
                        .get(
                            new Operation()
                                .operationId("fetchAllSeedlotStatus")
                                .summary("Fetch all the possible seed lot status")
                                .description(
                                    """
Fetch all the possible status for a seed lot and their descriptions.""")))
                .addPathItem(
                    "/api/seedlot-status/{code}",
                    new PathItem()
                        .get(
                            new Operation()
                                .operationId("fetchSeedlotStatus")
                                .summary("Fetch a single seed lot status")
                                .description("Fetch a seed lot status by its code."))));
  }
}
