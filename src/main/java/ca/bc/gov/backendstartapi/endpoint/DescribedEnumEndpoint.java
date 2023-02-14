package ca.bc.gov.backendstartapi.endpoint;

import ca.bc.gov.backendstartapi.dto.DescribedEnumDto;
import ca.bc.gov.backendstartapi.enumeration.DescribedEnum;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * A blueprint of endpoints for fetching {@link DescribedEnum DescribedEnums}. Ideally, it'll have
 * all the endpoints needed for these resources and the developer only needs to populate {@link
 * DescribedEnumEndpoint#enumClass} with the target class.
 *
 * @param <E> The type of the enum to be fetched. It must implement {@link DescribedEnum}
 */
public abstract class DescribedEnumEndpoint<E extends Enum<E> & DescribedEnum> {

  /** The class of the enum to be fetched by the endpoints. */
  protected Class<E> enumClass;

  /**
   * Fetch all the possible values of {@link E}.
   *
   * @return a list with all the possible values for {@code E} and their description
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<DescribedEnumDto<E>>> getAll() {
    var valueDtos = Arrays.stream(enumClass.getEnumConstants()).map(DescribedEnumDto::new).toList();
    return ResponseEntity.ok(valueDtos);
  }

  /**
   * Fetch a specific value of {@link E} and its description.
   *
   * @param code the name of the enum value to be fetched
   * @return the enum value named {@code code}, if it exists
   */
  @GetMapping(path = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DescribedEnumDto<E>> fetch(@PathVariable("code") String code) {
    var valueDto =
        Arrays.stream(enumClass.getEnumConstants())
            .dropWhile(v -> v.name().equals(code))
            .findFirst()
            .map(DescribedEnumDto::new);
    return ResponseEntity.of(valueDto);
  }
}
