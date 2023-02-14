package ca.bc.gov.backendstartapi.endpoint;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import ca.bc.gov.backendstartapi.enumeration.DescribedEnum;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.hamcrest.core.IsEqual;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

interface DescribedEnumEndpointTest<E extends Enum<E> & DescribedEnum> {

  /**
   * Generate an array of {@link ResultMatcher ResultMatchers} for the properties of {@link
   * DescribedEnum DescribedEnums}.
   *
   * @param values an array with the described enums to be checked
   * @return an array with matchers for each enum in {@code values}
   * @see ResultActions#andExpectAll(ResultMatcher...)
   */
  default ResultMatcher[] generateMatchers(E[] values) {
    return IntStream.range(0, values.length)
        .boxed()
        .flatMap(
            i ->
                Stream.of(
                    jsonPath(String.format("[%s].code", i), IsEqual.equalTo(values[i].name())),
                    jsonPath(
                        String.format("[%s].description", i),
                        IsEqual.equalTo(values[i].description()))))
        .toArray(ResultMatcher[]::new);
  }
}
