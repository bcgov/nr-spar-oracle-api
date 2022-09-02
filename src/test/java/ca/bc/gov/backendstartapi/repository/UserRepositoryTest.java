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
  private final String FIRSTNAME = "Ricardo";
  private final String LASTNAME = "Campos";

  private final UserDto USERDTO = UserDto.builder().firstName(FIRSTNAME).lastName(LASTNAME).build();

  @Test
  @DisplayName("Save user into repository")
  void saveTest() {
    userRepository.save(USERDTO);

    Mono<UserDto> userDtoMono = userRepository.find(USERDTO.getFirstName(), USERDTO.getLastName());

    StepVerifier.create(userDtoMono)
        .assertNext(
            userSaved -> {
              Assertions.assertFalse(userSaved.isEmpty());
              Assertions.assertEquals(FIRSTNAME, userSaved.getFirstName());
              Assertions.assertEquals(LASTNAME, userSaved.getLastName());
            })
        .verifyComplete();
  }

  @Test
  @DisplayName("2-Find by first name")
  void findByFirstNameTest() {
    userRepository.save(USERDTO);
    Flux<UserDto> userDtoFlux = userRepository.findByFirstName(FIRSTNAME);

    StepVerifier.create(userDtoFlux).expectComplete();

    UserDto userDto = userDtoFlux.blockFirst();
    Assertions.assertNotNull(userDto);
    Assertions.assertFalse(userDto.isEmpty());
    Assertions.assertEquals(FIRSTNAME, userDto.getFirstName());
    Assertions.assertEquals(LASTNAME, userDto.getLastName());
  }

  @Test
  @DisplayName("Find by last name")
  void findByLastNameTest() {
    userRepository.save(USERDTO);
    Flux<UserDto> userDtoFlux = userRepository.findByLastName(LASTNAME);

    StepVerifier.create(userDtoFlux)
        .assertNext(
            result -> {
              Assertions.assertFalse(result.isEmpty());
              Assertions.assertEquals(FIRSTNAME, result.getFirstName());
              Assertions.assertEquals(LASTNAME, result.getLastName());
            })
        .verifyComplete();
  }

  @Test
  @DisplayName("Find by first and last name")
  void findTest() {
    userRepository.save(USERDTO);
    Mono<UserDto> findMono = userRepository.find(FIRSTNAME, LASTNAME);

    StepVerifier.create(findMono)
        .assertNext(
            result -> {
              Assertions.assertFalse(result.isEmpty());
              Assertions.assertEquals(FIRSTNAME, result.getFirstName());
              Assertions.assertEquals(LASTNAME, result.getLastName());
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
              Assertions.assertEquals(FIRSTNAME, result.getFirstName());
              Assertions.assertEquals(LASTNAME, result.getLastName());
            })
        .verifyComplete();

    Mono<UserDto> findMono = userRepository.find(FIRSTNAME, LASTNAME);
    StepVerifier.create(findMono).expectComplete();

    Assertions.assertNull(findMono.block());
  }
}
