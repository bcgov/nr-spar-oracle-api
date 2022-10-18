package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.dto.UserDto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

  private final UserRepository userRepository = new UserRepository();
  private static final String FIRST_NAME = "Ricardo";
  private static final String LAST_NAME = "Campos";

  private static final UserDto USERDTO = new UserDto(FIRST_NAME, LAST_NAME);

  @Test
  @DisplayName("Save user into repository")
  void saveTest() {
    UserDto saved = userRepository.save(USERDTO);

    Assertions.assertNotNull(saved);
    Assertions.assertEquals(FIRST_NAME, saved.getFirstName());
    Assertions.assertEquals(LAST_NAME, saved.getLastName());
  }

  @Test
  @DisplayName("Find by first name")
  void findByFirstNameTest() {
    userRepository.save(USERDTO);

    List<UserDto> userList = userRepository.findAllByFirstName(FIRST_NAME);
    Assertions.assertEquals(1, userList.size());
    Assertions.assertEquals(FIRST_NAME, userList.get(0).getFirstName());
    Assertions.assertEquals(LAST_NAME, userList.get(0).getLastName());
  }

  @Test
  @DisplayName("Find by last name")
  void findByLastNameTest() {
    userRepository.save(USERDTO);

    List<UserDto> userList = userRepository.findAllByLastName(LAST_NAME);
    Assertions.assertEquals(1, userList.size());
    Assertions.assertEquals(FIRST_NAME, userList.get(0).getFirstName());
    Assertions.assertEquals(LAST_NAME, userList.get(0).getLastName());
  }

  @Test
  @DisplayName("Find by first and last name")
  void findTest() {
    userRepository.save(USERDTO);

    Optional<UserDto> userList = userRepository.find(FIRST_NAME, LAST_NAME);
    Assertions.assertFalse(userList.isEmpty());
    Assertions.assertEquals(FIRST_NAME, userList.get().getFirstName());
    Assertions.assertEquals(LAST_NAME, userList.get().getLastName());
  }

  @Test
  @DisplayName("Find All users test")
  void findAllTest() {
    userRepository.save(USERDTO);

    Collection<UserDto> userList = userRepository.findAll();
    Assertions.assertEquals(1, userList.size());
    UserDto userDto = new ArrayList<>(userList).get(0);
    Assertions.assertEquals(FIRST_NAME, userDto.getFirstName());
    Assertions.assertEquals(LAST_NAME, userDto.getLastName());
  }

  @Test
  @DisplayName("Delete user from the repository")
  void deleteTest() {
    userRepository.save(USERDTO);

    UserDto deleted = userRepository.delete(USERDTO);
    Assertions.assertNotNull(deleted);
    Assertions.assertEquals(FIRST_NAME, deleted.getFirstName());
    Assertions.assertEquals(LAST_NAME, deleted.getLastName());

    Optional<UserDto> find = userRepository.find(FIRST_NAME, LAST_NAME);
    Assertions.assertTrue(find.isEmpty());
  }
}
