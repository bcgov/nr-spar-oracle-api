package ca.bc.gov.backendstartapi.endpoint;

import ca.bc.gov.backendstartapi.dto.UserDto;
import ca.bc.gov.backendstartapi.exception.UserNotFoundException;
import ca.bc.gov.backendstartapi.repository.UserRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** This class exposes user related endpoints. */
@NoArgsConstructor
@RestController
@RequestMapping("/api/users")
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
   * @return a UserDto instance containing the new user info
   */
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('user_write')")
  public UserDto create(@Valid @RequestBody UserDto user) {
    return userRepository.save(user);
  }

  /**
   * Get a list of users given the last name.
   *
   * @param firstName user's first name
   * @return A List containing all found users.
   */
  @GetMapping(
      value = "/find-all-by-first-name/{firstName}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('user_read')")
  public List<UserDto> readByFirstName(@PathVariable("firstName") String firstName) {
    List<UserDto> userList = userRepository.findAllByFirstName(firstName);
    if (userList.isEmpty()) {
      throw new UserNotFoundException();
    }
    return userList;
  }

  /**
   * Get a list of users given the last name.
   *
   * @param lastName user's last name
   * @return A List containing all found users.
   */
  @GetMapping(
      value = "/find-all-by-last-name/{lastName}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('user_read')")
  public List<UserDto> readByLastName(@PathVariable("lastName") String lastName) {
    List<UserDto> userList = userRepository.findAllByLastName(lastName);
    if (userList.isEmpty()) {
      throw new UserNotFoundException();
    }
    return userList;
  }

  /**
   * Get a user given his first and last name.
   *
   * @param firstName user's first name
   * @param lastName user's last name
   * @return a UserDto instance containing the found user or a 404 if not found.
   */
  @GetMapping(value = "/find/{firstName}/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('user_read')")
  public UserDto readByUser(
      @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
    Optional<UserDto> userDtoOp = userRepository.find(firstName, lastName);
    if (userDtoOp.isEmpty()) {
      throw new UserNotFoundException();
    }

    return userDtoOp.get();
  }

  /**
   * Get a list with all registered users.
   *
   * @return a Collection containing all found users or a 404 if not found.
   */
  @GetMapping(value = "/find-all", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('user_read')")
  public Collection<UserDto> readAllUsers() {
    return userRepository.findAll();
  }

  /**
   * Delete a user given his first and last name.
   *
   * @param firstName user's first name
   * @param lastName user's last name
   * @return a UserDto instance containing the removed user info.
   */
  @DeleteMapping(value = "/{firstName}/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('user_write')")
  public UserDto deleteUser(
      @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
    return userRepository.delete(new UserDto(firstName, lastName));
  }
}
