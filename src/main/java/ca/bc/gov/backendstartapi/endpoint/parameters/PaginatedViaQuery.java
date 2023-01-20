package ca.bc.gov.backendstartapi.endpoint.parameters;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Adds the following pagination parameters to the Swagger documentation of a method:
 *
 * <ul>
 *   <li>page: Zero-based page index indicating the page to be returned; defaults to 0
 *   <li>perPage: The maximum number of results in a page; defaults to 20
 * </ul>
 *
 * <p>The code for such parameters, along with their behaviour, must be implemented and documented
 * by the developer. See {@link PaginationParameters} and its uses, for instance.
 *
 * <p>Concept taken from {@link org.springdoc.core.converters.models.PageableAsQueryParam}.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(
    in = ParameterIn.QUERY,
    description = "Zero-based page index indicating the page to be returned.",
    name = "page",
    schema = @Schema(type = "integer", defaultValue = "0", minimum = "0"))
@Parameter(
    in = ParameterIn.QUERY,
    description = "The maximum number of results in a page.",
    name = "perPage",
    schema = @Schema(type = "integer", defaultValue = "20", minimum = "1"))
public @interface PaginatedViaQuery {}
