package ca.bc.gov.backendstartapi.endpoint;

import ca.bc.gov.backendstartapi.dto.TestDto;
import ca.bc.gov.backendstartapi.local.TestRepository;
import ca.bc.gov.backendstartapi.local.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Just an endpoint to test the access to the local database.
 *
 * <p>TODO: remove before opening a PR.
 */
@RestController
@RequestMapping("/tests")
public class TestEndpoint {

  @Autowired private TestRepository testRepository;

  /**
   * Fetch a {@link Test} by its identifier.
   *
   * @param id the {@code Test}'s identifier
   * @return a DTO with the information of the {@code Test} fond, if any
   */
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public TestDto getTest(@PathVariable("id") long id) {
    return testRepository
        .findById(id)
        .map(test -> new TestDto(test.getId(), test.getName()))
        .orElseThrow();
  }

  /**
   * Create a new {@link Test}.
   *
   * @param testDto a DTO with the information about the test to be created. Must <em>not</em> have
   *     an identifier.
   * @return a DTO with the information of the {@code Test} created
   */
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public TestDto create(@RequestBody TestDto testDto) {
    if (testDto.id() != null) {
      throw new RuntimeException("The DTO sent must not have an identifier.");
    }
    var test = testRepository.save(new Test(testDto.id(), testDto.name()));
    return new TestDto(test.getId(), test.getName());
  }
}
