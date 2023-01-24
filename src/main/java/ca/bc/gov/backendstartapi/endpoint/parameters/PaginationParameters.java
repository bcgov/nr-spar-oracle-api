package ca.bc.gov.backendstartapi.endpoint.parameters;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Pagination parameters to be used in the processing of HTTP GET requests.
 *
 * @param page The page to be returned. Zero-based, and must be non-negative; defaults to 0
 * @param perPage The maximum number of results in each page. Defaults to 20
 */
@Hidden
public record PaginationParameters(@PositiveOrZero Integer page, @Positive Integer perPage) {

  /**
   * Build an instance of {@link PaginationParameters}, using the default values for {@code page}
   * and {@code perPage} if they're null.
   */
  public PaginationParameters {
    if (page == null) {
      page = 0;
    }
    if (perPage == null) {
      perPage = 20;
    }
  }

  /** Get the number of results to be skipped by the search. Useful for SQL queries. */
  public int skip() {
    return page * perPage;
  }
}
