package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.VegetationCode;
import java.util.List;
import java.util.Optional;

/** A repository to fetch {@link VegetationCode vegetation codes}. */
public interface VegetationCodeRepository {

  /**
   * Fetch a vegetation code by its identifier, if such code exists.
   *
   * @param code the identifier of the vegetation code sought
   * @return information about the vegetation code identified by {@code code}, if one exists
   */
  Optional<VegetationCode> findByCode(String code);

  /**
   * Paginated search for valid vegetation codes by their identifier or description.
   *
   * @param search a string to which the codes' identifiers and descriptions should be matched
   * @param skip the number of results to skip
   * @param maxResults the maximum number of results to be returned
   * @return a list with up to {@code pageSize} results that match {@code search}, ordered by their
   *     identifiers
   */
  List<VegetationCode> findValidByCodeOrDescription(String search, int skip, int maxResults);
}
