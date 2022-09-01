package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.dto.UserDto;
import ca.bc.gov.backendstartapi.exception.UserExistsException;
import ca.bc.gov.backendstartapi.util.ObjectUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** This class is a sample class to handle users first and last name in memory. */
@Repository
public class UserRepository {

  private final Map<Integer, UserDto> users = new HashMap<>();
  private final Map<String, List<Integer>> firstNameIndex = new HashMap<>();
  private final Map<String, List<Integer>> lastNameIndex = new HashMap<>();

  /**
   * Save a user.
   *
   * @param userDto user to be saved
   * @return the new saved user.
   */
  public Mono<UserDto> save(UserDto userDto) {
    UserDto saved = users.put(userDto.hashCode(), userDto);
    if (!Objects.isNull(saved)) {
      return Mono.error(new UserExistsException());
    }

    firstNameIndex.putIfAbsent(userDto.getFirstName(), new ArrayList<>());
    lastNameIndex.putIfAbsent(userDto.getLastName(), new ArrayList<>());

    firstNameIndex.get(userDto.getFirstName()).add(userDto.hashCode());
    lastNameIndex.get(userDto.getLastName()).add(userDto.hashCode());

    return Mono.just(userDto);
  }

  /**
   * Find users by first name.
   *
   * @param firstName the user's first name
   * @return a list with all possible users
   */
  public Flux<UserDto> findByFirstName(String firstName) {
    List<Integer> indexes = firstNameIndex.get(firstName);
    if (ObjectUtil.isEmptyOrNull(indexes)) {
      return Flux.empty();
    }

    return createFromIndexes(indexes);
  }

  /**
   * Find users by last name.
   *
   * @param lastName the user's last name
   * @return a list with all possible users
   */
  public Flux<UserDto> findByLastName(String lastName) {
    List<Integer> indexes = lastNameIndex.get(lastName);
    if (ObjectUtil.isEmptyOrNull(indexes)) {
      return Flux.empty();
    }

    return createFromIndexes(indexes);
  }

  /**
   * Find a specific user by its name.
   *
   * @param firstName the user's first name
   * @param lastName the user's last name
   * @return a UserDto if found
   */
  public Mono<UserDto> find(String firstName, String lastName) {
    UserDto userDtoToFind = UserDto.builder().firstName(firstName).lastName(lastName).build();

    UserDto userDb = users.get(userDtoToFind.hashCode());
    if (Objects.isNull(userDb)) {
      return Mono.empty();
    }

    return Mono.just(userDb);
  }

  /**
   * Delete a user from the repository.
   *
   * @param userDto user to be deleted
   * @return a Mono instance containing the deleted user data
   */
  public Mono<UserDto> delete(UserDto userDto) {
    UserDto removed = users.remove(userDto.hashCode());
    return Mono.just(removed);
  }

  /**
   * Create a Flux with all users that were found.
   *
   * @param indexes a list of integer with all indexes found
   * @return a list with all possible users
   */
  private Flux<UserDto> createFromIndexes(List<Integer> indexes) {
    final List<UserDto> userList = new ArrayList<>();
    indexes.forEach(index -> userList.add(users.get(index)));
    return Flux.fromIterable(userList);
  }
}
