package ca.bc.gov.backendstartapi.endpoint;

import static org.hamcrest.Matchers.equalTo;

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
public class UserEndpointIT {

  @Autowired private WebTestClient webTestClient;

  @MockBean UserRepository userRepository;

  @Test
  @DisplayName("Create user with success")
  void createSuccess() {
    UserDto userDto = UserDto.builder().firstName("Ricardo").lastName("Campos").build();

    Mockito.when(userRepository.saveUser(userDto, true)).thenReturn(Mono.just(userDto));

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(userDto), UserDto.class)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.firstName")
        .isEqualTo("Ricardo")
        .jsonPath("$.lastName")
        .isEqualTo("Campos");
  }

  @Test
  @DisplayName("Create user without firstName")
  void createWithoutFirstName() {
    UserDto userDto = UserDto.builder().lastName("Campos").build();

    Mockito.when(userRepository.saveUser(userDto, true)).thenThrow(WebExchangeBindException.class);

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(userDto), UserDto.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(String.class)
        .value(equalTo("{\"firstName\":\"must not be null\"}"));
  }

  @Test
  @DisplayName("Create user without lastName")
  void createWithoutLastName() {
    UserDto userDto = UserDto.builder().firstName("Ricardo").build();

    Mockito.when(userRepository.saveUser(userDto, true)).thenThrow(WebExchangeBindException.class);

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(userDto), UserDto.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(String.class)
        .value(equalTo("{\"lastName\":\"must not be null\"}"));
  }

  @Test
  @DisplayName("Create user with minimum lastName size")
  void createSizeMin() {
    UserDto userDto = UserDto.builder().firstName("Ricardo").lastName("C").build();

    Mockito.when(userRepository.saveUser(userDto, true)).thenThrow(WebExchangeBindException.class);

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(userDto), UserDto.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(String.class)
        .value(equalTo("{\"lastName\":\"size must be between 2 and 20\"}"));
  }

  @Test
  @DisplayName("Create user with maximum lastName size")
  void createSizeMax() {
    UserDto userDto =
        UserDto.builder().firstName("Ricardo").lastName("CamposCamposCamposCampos").build();

    Mockito.when(userRepository.saveUser(userDto, true)).thenThrow(WebExchangeBindException.class);

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(userDto), UserDto.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(String.class)
        .value(equalTo("{\"lastName\":\"size must be between 2 and 20\"}"));
  }

  // create error duplicate
  @Test
  @DisplayName("Try to create existing user")
  void createExisting() {
    UserDto userDto = UserDto.builder().firstName("Ricardo").lastName("Campos").build();
    Mockito.when(userRepository.saveUser(userDto, true)).thenReturn(Mono.just(userDto));

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(userDto), UserDto.class)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.firstName")
        .isEqualTo("Ricardo")
        .jsonPath("$.lastName")
        .isEqualTo("Campos");

    Mockito.when(userRepository.saveUser(userDto, true)).thenThrow(UserExistsException.class);

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(userDto), UserDto.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(String.class)
        .value(equalTo("{\"message\":\"User already registered!\"}"));
  }

  @Test
  @DisplayName("Find users by first name")
  void findUsersByFirstName() {
    UserDto userDto = UserDto.builder().firstName("Ricardo").lastName("Campos").build();
    Mockito.when(userRepository.saveUser(userDto, true)).thenReturn(Mono.just(userDto));

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(userDto), UserDto.class)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.firstName")
        .isEqualTo("Ricardo")
        .jsonPath("$.lastName")
        .isEqualTo("Campos");

    List<UserDto> users = new ArrayList<>(Collections.singletonList(userDto));
    Mockito.when(userRepository.findUserByFirstName("Ricardo"))
        .thenReturn(Flux.fromIterable(users));

    final Map<String, String> params = new HashMap<>();
    params.put("firstName", "Ricardo");

    webTestClient
        .get()
        .uri("/users/find-by-first-name/{firstName}", params)
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
    UserDto userDto = UserDto.builder().firstName("Ricardo").lastName("Campos").build();

    Mockito.when(userRepository.saveUser(userDto, true)).thenReturn(Mono.just(userDto));

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(userDto), UserDto.class)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.firstName")
        .isEqualTo("Ricardo")
        .jsonPath("$.lastName")
        .isEqualTo("Campos");

    List<UserDto> users = new ArrayList<>(Collections.singletonList(userDto));
    Mockito.when(userRepository.findUserByLastName("Campos")).thenReturn(Flux.fromIterable(users));

    final Map<String, String> params = new HashMap<>();
    params.put("lastName", "Campos");

    webTestClient
        .get()
        .uri("/users/find-by-last-name/{lastName}", params)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBodyList(UserDto.class);
  }

  @Test
  @DisplayName("Find users by last name not found")
  void findUsersNotFound() {
    final Map<String, String> params = new HashMap<>();
    params.put("firstName", "RRR");

    Flux<UserDto> userDtoFlux = Flux.empty();
    Mockito.when(userRepository.findUserByFirstName("RRR")).thenReturn(userDtoFlux);

    webTestClient
        .get()
        .uri("/users/find-by-first-name/{firstName}", params)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(String.class)
        .value(equalTo("{\"message\":\"User not registered!\"}"));
  }

  @Test
  @DisplayName("Delete user that doesn't exist")
  void deleteUserDoesNotExist() {
    UserDto userDto = UserDto.builder().firstName("Ricardo").lastName("Campos").build();

    final Map<String, String> params = new HashMap<>();
    params.put("firstName", userDto.getFirstName());
    params.put("lastName", userDto.getLastName());

    Mockito.when(userRepository.findUser(userDto.getFirstName(), userDto.getLastName()))
        .thenThrow(UserNotFoundException.class);

    webTestClient
        .delete()
        .uri("/users/{firstName}/{lastName}", params)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(String.class)
        .value(equalTo("{\"message\":\"User not registered!\"}"));
  }
}
