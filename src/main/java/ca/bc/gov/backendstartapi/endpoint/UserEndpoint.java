package ca.bc.gov.backendstartapi.endpoint;

import ca.bc.gov.backendstartapi.dto.UserDto;
import ca.bc.gov.backendstartapi.exception.UserExistsException;
import ca.bc.gov.backendstartapi.exception.UserNotFoundException;
import ca.bc.gov.backendstartapi.repository.UserRepository;
import javax.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** This class exposes user related endpoints. */
@CrossOrigin(originPatterns = {"http://localhost:3000", "https://*.apps.silver.devops.gov.bc.ca"})
@NoArgsConstructor
@RestController
@RequestMapping("/users")
@Setter
public class UserEndpoint {

  private UserRepository userRepository;

  @Autowired
  public UserEndpoint(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Create a user with first and last name.
   *
   * @param user a UserDto containing both first and last name
   * @return a Mono instance containing the new user info
   */
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<UserDto> create(@Valid @RequestBody UserDto user) {
    return userRepository.save(user).onErrorResume(UserExistsException.class, Mono::error);
  }

  /**
   * Get a list of users given the last name.
   *
   * @param firstName user's first name
   * @return A Flux instance containing all found users.
   */
  @GetMapping(
      value = "/find-by-first-name/{firstName}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Flux<UserDto> readByFirstName(@PathVariable("firstName") String firstName) {
    return userRepository
        .findByFirstName(firstName)
        .switchIfEmpty(Mono.error(new UserNotFoundException()));
  }

  /**
   * Get a list of users given the last name.
   *
   * @param lastName user's last name
   * @return A Flux instance containing all found users.
   */
  @GetMapping(value = "/find-by-last-name/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Flux<UserDto> readByLastName(@PathVariable("lastName") String lastName) {
    return userRepository
        .findByLastName(lastName)
        .switchIfEmpty(Mono.error(new UserNotFoundException()));
  }

  /**
   * Get a user given his first and last name.
   *
   * @param firstName user's first name
   * @param lastName user's last name
   * @return a Mono instance containing the found user or a 404 if not found.
   */
  @GetMapping(value = "/find/{firstName}/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<UserDto> readByUser(
      @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
    return userRepository
        .find(firstName, lastName)
        .switchIfEmpty(Mono.error(new UserNotFoundException()));
  }

  /**
   * Delete a user given his first and last name.
   *
   * @param firstName user's first name
   * @param lastName user's last name
   * @return a Mono instance containing the removed user info
   */
  @DeleteMapping(value = "/{firstName}/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<UserDto> deleteUser(
      @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
    return userRepository
        .find(firstName, lastName)
        .flatMap(user -> userRepository.delete(user))
        .switchIfEmpty(Mono.error(new UserNotFoundException()));
  }
}
