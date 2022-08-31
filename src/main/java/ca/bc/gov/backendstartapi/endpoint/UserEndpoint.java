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

@CrossOrigin
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

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<UserDto> create(@Valid @RequestBody UserDto user) {
    return userRepository
        .saveUser(user, true)
        .onErrorResume(UserExistsException.class, Mono::error);
  }

  @GetMapping(
      value = "/find-by-first-name/{firstName}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Flux<UserDto> readByFirstName(@PathVariable("firstName") String firstName) {
    return userRepository
        .findUserByFirstName(firstName)
        .switchIfEmpty(Mono.error(new UserNotFoundException()));
  }

  @GetMapping(value = "/find-by-last-name/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Flux<UserDto> readByLastName(@PathVariable("lastName") String lastName) {
    return userRepository
        .findUserByLastName(lastName)
        .switchIfEmpty(Mono.error(new UserNotFoundException()));
  }

  @GetMapping(value = "/find/{firstName}/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<UserDto> readByUser(
      @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
    return userRepository
        .findUser(firstName, lastName)
        .switchIfEmpty(Mono.error(new UserNotFoundException()));
  }

  @DeleteMapping(value = "/{firstName}/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<UserDto> deleteUser(
      @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
    return userRepository
        .findUser(firstName, lastName)
        .flatMap(user -> userRepository.delete(user))
        .switchIfEmpty(Mono.error(new UserNotFoundException()));
  }
}
