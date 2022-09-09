package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

  private final UserRepository userRepository = new UserRepository();
  private static final String FIRST_NAME = "Ricardo";
  private static final String LAST_NAME = "Campos";

  private static final UserDto USERDTO = new UserDto(FIRST_NAME, LAST_NAME);

  @Test
  @DisplayName("Save user into repository")
  void saveTest() {
    userRepository.save(USERDTO);

    Mono<UserDto> userDtoMono =
        userRepository.find(USERDTO.getFirstName(), USERDTO.getLastName());

    StepVerifier.create(userDtoMono)
        .assertNext(
            userSaved -> {
              Assertions.assertFalse(userSaved.isEmpty());
              Assertions.assertEquals(FIRST_NAME, userSaved.getFirstName());
              Assertions.assertEquals(LAST_NAME, userSaved.getLastName());
            })
        .verifyComplete();
  }

  @Test
  @DisplayName("Find by first name")
  void findByFirstNameTest() {
    userRepository.save(USERDTO);
    Flux<UserDto> userDtoFlux = userRepository.findAllByFirstName(FIRST_NAME);

    StepVerifier.create(userDtoFlux).expectComplete();

    UserDto userDto = userDtoFlux.blockFirst();
    Assertions.assertNotNull(userDto);
    Assertions.assertFalse(userDto.isEmpty());
    Assertions.assertEquals(FIRST_NAME, userDto.getFirstName());
    Assertions.assertEquals(LAST_NAME, userDto.getLastName());
  }

  @Test
  @DisplayName("Find by last name")
  void findByLastNameTest() {
    userRepository.save(USERDTO);
    Flux<UserDto> userDtoFlux = userRepository.findByLastName(LAST_NAME);

    StepVerifier.create(userDtoFlux)
        .assertNext(
            result -> {
              Assertions.assertFalse(result.isEmpty());
              Assertions.assertEquals(FIRST_NAME, result.getFirstName());
              Assertions.assertEquals(LAST_NAME, result.getLastName());
            })
        .verifyComplete();
  }

  @Test
  @DisplayName("Find by first and last name")
  void findTest() {
    userRepository.save(USERDTO);
    Mono<UserDto> findMono = userRepository.find(FIRST_NAME, LAST_NAME);

    StepVerifier.create(findMono)
        .assertNext(
            result -> {
              Assertions.assertFalse(result.isEmpty());
              Assertions.assertEquals(FIRST_NAME, result.getFirstName());
              Assertions.assertEquals(LAST_NAME, result.getLastName());
            })
        .verifyComplete();
  }

  @Test
  @DisplayName("Find All users test")
  void findAllTest() {
    userRepository.save(USERDTO);
    Flux<UserDto> usersFlux = userRepository.findAll();

    StepVerifier.create(usersFlux)
        .assertNext(
            result -> {
              Assertions.assertFalse(result.isEmpty());
              Assertions.assertEquals(FIRST_NAME, result.getFirstName());
              Assertions.assertEquals(LAST_NAME, result.getLastName());
            })
        .verifyComplete();
  }

  @Test
  @DisplayName("Delete user from the repository")
  void deleteTest() {
    userRepository.save(USERDTO);
    Mono<UserDto> deleteMono = userRepository.delete(USERDTO);

    StepVerifier.create(deleteMono)
        .assertNext(
            result -> {
              Assertions.assertFalse(result.isEmpty());
              Assertions.assertEquals(FIRST_NAME, result.getFirstName());
              Assertions.assertEquals(LAST_NAME, result.getLastName());
            })
        .verifyComplete();

    Mono<UserDto> findMono = userRepository.find(FIRST_NAME, LAST_NAME);
    StepVerifier.create(findMono).expectComplete();

    Assertions.assertNull(findMono.block());
  }
}
