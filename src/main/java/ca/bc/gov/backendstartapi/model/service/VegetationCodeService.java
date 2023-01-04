package ca.bc.gov.backendstartapi.model.service;

import ca.bc.gov.backendstartapi.model.VegetationCode;
import java.util.List;
import java.util.Optional;

/** Used for the manipulation of {@link VegetationCode vegetation codes}. */
public interface VegetationCodeService {

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
   * @param page the page that should be returned
   * @param pageSize the maximum number of results to be returned
   * @return a list with up to {@code pageSize} results that match {@code search}, ordered by their
   *     identifiers
   */
  List<VegetationCode> findValidByCodeOrDescription(String search, int page, int pageSize);
}
