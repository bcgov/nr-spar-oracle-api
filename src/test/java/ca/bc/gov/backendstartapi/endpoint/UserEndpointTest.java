package ca.bc.gov.backendstartapi.endpoint;

import ca.bc.gov.backendstartapi.dto.UserDto;
import ca.bc.gov.backendstartapi.exception.UserExistsException;
import ca.bc.gov.backendstartapi.exception.UserNotFoundException;
import ca.bc.gov.backendstartapi.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserEndpoint.class)
@Import(UserRepository.class)
class UserEndpointTest {

  private static final String FIRST_NAME = "Ricardo";
  private static final String LAST_NAME = "Campos";
  private static final UserDto USERDTO = new UserDto(FIRST_NAME, LAST_NAME);

  @Autowired private WebTestClient webTestClient;

  @MockBean UserRepository userRepository;

  @Test
  @DisplayName("Create user with success")
  void createSuccess() {
    Mockito.when(userRepository.save(USERDTO)).thenReturn(Mono.just(USERDTO));

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(USERDTO), UserDto.class)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.firstName")
        .isEqualTo(FIRST_NAME)
        .jsonPath("$.lastName")
        .isEqualTo(LAST_NAME);
  }

  @Test
  @DisplayName("Create user without firstName")
  void createWithoutFirstName() {
    UserDto userDtoPartial = new UserDto(null, LAST_NAME);

    Mockito.when(userRepository.save(userDtoPartial)).thenThrow(WebExchangeBindException.class);

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(userDtoPartial), UserDto.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.errorMessage")
        .isEqualTo("1 field(s) with validation problem!")
        .jsonPath("$.fields[0].fieldName")
        .isEqualTo("firstName")
        .jsonPath("$.fields[0].fieldMessage")
        .isEqualTo("must not be blank");
  }

  @Test
  @DisplayName("Create user without lastName")
  void createWithoutLastName() {
    UserDto userDtoPartial = new UserDto(FIRST_NAME, null);

    Mockito.when(userRepository.save(userDtoPartial)).thenThrow(WebExchangeBindException.class);

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(userDtoPartial), UserDto.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.errorMessage")
        .isEqualTo("1 field(s) with validation problem!")
        .jsonPath("$.fields[0].fieldName")
        .isEqualTo("lastName")
        .jsonPath("$.fields[0].fieldMessage")
        .isEqualTo("must not be blank");
  }

  @Test
  @DisplayName("Create user with minimum lastName size")
  void createSizeMin() {
    UserDto userDtoError = new UserDto(FIRST_NAME, "C");

    Mockito.when(userRepository.save(userDtoError)).thenThrow(WebExchangeBindException.class);

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(userDtoError), UserDto.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.errorMessage")
        .isEqualTo("1 field(s) with validation problem!")
        .jsonPath("$.fields[0].fieldName")
        .isEqualTo("lastName")
        .jsonPath("$.fields[0].fieldMessage")
        .isEqualTo("size must be between 2 and 20");
  }

  @Test
  @DisplayName("Create user with maximum lastName size")
  void createSizeMax() {
    UserDto userDtoError = new UserDto("Ricardo", "CamposCamposCamposCampos");

    Mockito.when(userRepository.save(userDtoError)).thenThrow(WebExchangeBindException.class);

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(userDtoError), UserDto.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.errorMessage")
        .isEqualTo("1 field(s) with validation problem!")
        .jsonPath("$.fields[0].fieldName")
        .isEqualTo("lastName")
        .jsonPath("$.fields[0].fieldMessage")
        .isEqualTo("size must be between 2 and 20");
  }

  @Test
  @DisplayName("Try to create existing user")
  void createExisting() {
    Mockito.when(userRepository.save(USERDTO)).thenReturn(Mono.just(USERDTO));

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(USERDTO), UserDto.class)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.firstName")
        .isEqualTo(FIRST_NAME)
        .jsonPath("$.lastName")
        .isEqualTo(LAST_NAME);

    Mockito.when(userRepository.save(USERDTO)).thenThrow(UserExistsException.class);

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(USERDTO), UserDto.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(UserExistsException.class);
  }

  @Test
  @DisplayName("Find users by first name")
  void findUsersByFirstName() {
    Mockito.when(userRepository.save(USERDTO)).thenReturn(Mono.just(USERDTO));

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(USERDTO), UserDto.class)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.firstName")
        .isEqualTo(FIRST_NAME)
        .jsonPath("$.lastName")
        .isEqualTo(LAST_NAME);

    List<UserDto> users = new ArrayList<>(Collections.singletonList(USERDTO));
    Mockito.when(userRepository.findAllByFirstName(FIRST_NAME))
        .thenReturn(Flux.fromIterable(users));

    final Map<String, String> params = new HashMap<>();
    params.put("firstName", FIRST_NAME);

    webTestClient
        .get()
        .uri("/users/find-all-by-first-name/{firstName}", params)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBodyList(UserDto.class);
  }

  @Test
  @DisplayName("Find users by last name")
  void findUsersByLastName() {
    Mockito.when(userRepository.save(USERDTO)).thenReturn(Mono.just(USERDTO));

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(USERDTO), UserDto.class)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.firstName")
        .isEqualTo(FIRST_NAME)
        .jsonPath("$.lastName")
        .isEqualTo(LAST_NAME);

    List<UserDto> users = new ArrayList<>(Collections.singletonList(USERDTO));
    Mockito.when(userRepository.findByLastName(LAST_NAME)).thenReturn(Flux.fromIterable(users));

    final Map<String, String> params = new HashMap<>();
    params.put("lastName", LAST_NAME);

    webTestClient
        .get()
        .uri("/users/find-all-by-last-name/{lastName}", params)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBodyList(UserDto.class);
  }

  @Test
  @DisplayName("Find users by first and last name")
  void findUsersByFirstAndLastName() {
    Mockito.when(userRepository.save(USERDTO)).thenReturn(Mono.just(USERDTO));

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(USERDTO), UserDto.class)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.firstName")
        .isEqualTo(FIRST_NAME)
        .jsonPath("$.lastName")
        .isEqualTo(LAST_NAME);

    Mockito.when(userRepository.find(FIRST_NAME, LAST_NAME)).thenReturn(Mono.just(USERDTO));

    final Map<String, String> params = new HashMap<>();
    params.put("firstName", FIRST_NAME);
    params.put("lastName", LAST_NAME);

    webTestClient
        .get()
        .uri("/users/find/{firstName}/{lastName}", params)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.firstName")
        .isEqualTo(FIRST_NAME)
        .jsonPath("$.lastName")
        .isEqualTo(LAST_NAME);
  }

  @Test
  @DisplayName("Find all users")
  void findAllUsers() {
    Mockito.when(userRepository.save(USERDTO)).thenReturn(Mono.just(USERDTO));

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(USERDTO), UserDto.class)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.firstName")
        .isEqualTo(FIRST_NAME)
        .jsonPath("$.lastName")
        .isEqualTo(LAST_NAME);

    Mockito.when(userRepository.findAll())
        .thenReturn(Flux.fromIterable(Collections.singletonList(USERDTO)));

    webTestClient
        .get()
        .uri("/users/find-all")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$[0].firstName")
        .isEqualTo(FIRST_NAME)
        .jsonPath("$[0].lastName")
        .isEqualTo(LAST_NAME);
  }

  @Test
  @DisplayName("Find users by last name not found")
  void findUsersNotFound() {
    final Map<String, String> params = new HashMap<>();
    params.put("firstName", "RRR");

    Flux<UserDto> userDtoFlux = Flux.empty();
    Mockito.when(userRepository.findAllByFirstName("RRR")).thenReturn(userDtoFlux);

    webTestClient
        .get()
        .uri("/users/find-all-by-first-name/{firstName}", params)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.errorMessage")
        .isEqualTo("User not registered!");
  }

  @Test
  @DisplayName("Delete user that doesn't exist")
  void deleteUserDoesNotExist() {
    final Map<String, String> params = new HashMap<>();
    params.put("firstName", FIRST_NAME);
    params.put("lastName", LAST_NAME);

    Mockito.when(userRepository.find(FIRST_NAME, LAST_NAME)).thenThrow(UserNotFoundException.class);

    webTestClient
        .delete()
        .uri("/users/{firstName}/{lastName}", params)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(UserNotFoundException.class);
  }
}
