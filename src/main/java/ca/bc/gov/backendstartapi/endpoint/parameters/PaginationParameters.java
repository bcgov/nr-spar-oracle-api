package ca.bc.gov.backendstartapi.endpoint.parameters;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Pagination parameters to be used in the processing of HTTP GET requests.
 *
 * <p>Each page contains up to {@code pageSize} results, excluding the first {@code page * pageSize}
 * results. For instance, <code>{page = 2, pageSize = 10}</code> will exclude the first 20 results,
 * returning results 21 up to 30.
 *
 * @param page The page to be returned. Zero-based, and must be non-negative; defaults to 0
 * @param pageSize The maximum number of results to be returned. Defaults to 20
 */
public record PaginationParameters(@PositiveOrZero Integer page, @Positive Integer pageSize) {

  /**
   * Build an instance of {@link PaginationParameters}, using the default values for {@code page}
   * and {@code pageSize} if they're null.
   */
  public PaginationParameters {
    if (page == null) {
      page = 0;
    }
    if (pageSize == null) {
      pageSize = 20;
    }
  }
}
