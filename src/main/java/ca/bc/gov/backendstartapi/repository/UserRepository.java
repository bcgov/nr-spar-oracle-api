package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.dto.UserDto;
import ca.bc.gov.backendstartapi.exception.UserExistsException;
import ca.bc.gov.backendstartapi.exception.UserNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Repository;


/** This class is a sample class to handle users first and last name in memory. */
@Repository
public class UserRepository {

  private final Map<Integer, UserDto> users = new HashMap<>();

  /**
   * Save a user.
   *
   * @param userDto user to be saved
   * @return the new saved user.
   * @throws UserExistsException if the user already exists.
   */
  public UserDto save(UserDto userDto) {
    UserDto saved = users.put(userDto.hashCode(), userDto);
    if (!Objects.isNull(saved)) {
      throw new UserExistsException();
    }

    return userDto;
  }

  /**
   * Find users by first name.
   *
   * @param firstName the user's first name
   * @return a list with all possible users
   */
  public List<UserDto> findAllByFirstName(String firstName) {
    final List<UserDto> usersFound = new ArrayList<>();
    users
        .values()
        .forEach(
            dto -> {
              if (dto.getFirstName().equals(firstName)) {
                usersFound.add(dto);
              }
            });
    return usersFound;
  }

  /**
   * Find users by last name.
   *
   * @param lastName the user's last name
   * @return a list with all possible users
   */
  public List<UserDto> findAllByLastName(String lastName) {
    final List<UserDto> usersFound = new ArrayList<>();
    users
        .values()
        .forEach(
            dto -> {
              if (dto.getLastName().equals(lastName)) {
                usersFound.add(dto);
              }
            });
    return usersFound;
  }

  /**
   * Find a specific user by its name.
   *
   * @param firstName the user's first name
   * @param lastName the user's last name
   * @return an Optional of UserDto if found, or Empty Optional otherwise.
   */
  public Optional<UserDto> find(String firstName, String lastName) {
    UserDto userDtoToFind = new UserDto(firstName, lastName);

    UserDto userDb = users.get(userDtoToFind.hashCode());
    if (Objects.isNull(userDb)) {
      return Optional.empty();
    }

    return Optional.of(userDb);
  }

  /**
   * Find all users.
   *
   * @return a collection of UserDto
   */
  public Collection<UserDto> findAll() {
    return users.values();
  }

  /**
   * Delete a user from the repository.
   *
   * @param userDto user to be deleted
   * @return a UserDto instance containing the deleted user data
   * @throws UserNotFoundException if the user doesn't exist.
   */
  public UserDto delete(UserDto userDto) {
    UserDto removed = users.remove(userDto.hashCode());
    if (removed == null) {
      throw new UserNotFoundException();
    }
    return removed;
  }
}
