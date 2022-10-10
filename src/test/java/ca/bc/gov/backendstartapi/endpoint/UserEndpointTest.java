package ca.bc.gov.backendstartapi.endpoint;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.bc.gov.backendstartapi.dto.UserDto;
import ca.bc.gov.backendstartapi.exception.UserExistsException;
import ca.bc.gov.backendstartapi.exception.UserNotFoundException;
import ca.bc.gov.backendstartapi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class UserEndpointTest {

  private static final String FIRST_NAME = "Ricardo";
  private static final String LAST_NAME = "Campos";
  private static final UserDto USERDTO = new UserDto(FIRST_NAME, LAST_NAME);

  private MockMvc mockMvc;

  @MockBean UserRepository userRepository;

  @Autowired private WebApplicationContext webApplicationContext;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  private String getUserDtoString(UserDto userDto) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(userDto);
  }

  @Test
  @DisplayName("Create user with success")
  void createSuccess() throws Exception {
    Mockito.when(userRepository.save(USERDTO)).thenReturn(USERDTO);

    mockMvc
        .perform(
            post("/users")
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(getUserDtoString(USERDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
        .andExpect(jsonPath("$.lastName").value(LAST_NAME))
        .andReturn();
  }

  @Test
  @DisplayName("Create user without firstName")
  void createWithoutFirstName() throws Exception {
    UserDto userDtoPartial = new UserDto(null, LAST_NAME);

    mockMvc
        .perform(
            post("/users")
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(getUserDtoString(userDtoPartial)))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("$.errorMessage").value("1 field(s) with validation problem!"))
        .andExpect(jsonPath("$.fields[0].fieldName").value("firstName"))
        .andExpect(jsonPath("$.fields[0].fieldMessage").value("must not be blank"))
        .andReturn();
  }

  @Test
  @DisplayName("Create user without lastName")
  void createWithoutLastName() throws Exception {
    UserDto userDtoPartial = new UserDto(FIRST_NAME, null);

    mockMvc
        .perform(
            post("/users")
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(getUserDtoString(userDtoPartial)))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("$.errorMessage").value("1 field(s) with validation problem!"))
        .andExpect(jsonPath("$.fields[0].fieldName").value("lastName"))
        .andExpect(jsonPath("$.fields[0].fieldMessage").value("must not be blank"))
        .andReturn();
  }

  @Test
  @DisplayName("Create user with minimum lastName size")
  void createSizeMin() throws Exception {
    UserDto userDtoError = new UserDto(FIRST_NAME, "C");

    mockMvc
        .perform(
            post("/users")
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(getUserDtoString(userDtoError)))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("$.errorMessage").value("1 field(s) with validation problem!"))
        .andExpect(jsonPath("$.fields[0].fieldName").value("lastName"))
        .andExpect(jsonPath("$.fields[0].fieldMessage").value("size must be between 2 and 20"))
        .andReturn();
  }

  @Test
  @DisplayName("Create user with maximum lastName size")
  void createSizeMax() throws Exception {
    UserDto userDtoError = new UserDto("Ricardo", "CamposCamposCamposCampos");

    mockMvc
        .perform(
            post("/users")
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(getUserDtoString(userDtoError)))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("$.errorMessage").value("1 field(s) with validation problem!"))
        .andExpect(jsonPath("$.fields[0].fieldName").value("lastName"))
        .andExpect(jsonPath("$.fields[0].fieldMessage").value("size must be between 2 and 20"))
        .andReturn();
  }

  @Test
  @DisplayName("Try to create existing user")
  void createExisting() throws Exception {
    Mockito.when(userRepository.save(USERDTO)).thenReturn(USERDTO);

    mockMvc
        .perform(
            post("/users")
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(getUserDtoString(USERDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
        .andExpect(jsonPath("$.lastName").value(LAST_NAME))
        .andReturn();

    Mockito.when(userRepository.save(USERDTO)).thenThrow(UserExistsException.class);

    mockMvc
        .perform(
            post("/users")
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(getUserDtoString(USERDTO)))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        .andExpect(
            result ->
                Assertions.assertTrue(result.getResolvedException() instanceof UserExistsException))
        .andReturn();
  }

  @Test
  @DisplayName("Find users by first name")
  void findUsersByFirstName() throws Exception {
    Mockito.when(userRepository.save(USERDTO)).thenReturn(USERDTO);

    mockMvc
        .perform(
            post("/users")
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(getUserDtoString(USERDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
        .andExpect(jsonPath("$.lastName").value(LAST_NAME))
        .andReturn();

    List<UserDto> users = new ArrayList<>(Collections.singletonList(USERDTO));
    Mockito.when(userRepository.findAllByFirstName(FIRST_NAME)).thenReturn(users);

    mockMvc
        .perform(
            get("/users/find-all-by-first-name/{firstName}", FIRST_NAME)
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].firstName").value(FIRST_NAME))
        .andExpect(jsonPath("$[0].lastName").value(LAST_NAME))
        .andReturn();
  }

  @Test
  @DisplayName("Find users by last name")
  void findUsersByLastName() throws Exception {
    Mockito.when(userRepository.save(USERDTO)).thenReturn(USERDTO);

    mockMvc
        .perform(
            post("/users")
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(getUserDtoString(USERDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
        .andExpect(jsonPath("$.lastName").value(LAST_NAME))
        .andReturn();

    List<UserDto> users = new ArrayList<>(Collections.singletonList(USERDTO));
    Mockito.when(userRepository.findAllByLastName(LAST_NAME)).thenReturn(users);

    mockMvc
        .perform(
            get("/users/find-all-by-last-name/{lastName}", LAST_NAME)
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].firstName").value(FIRST_NAME))
        .andExpect(jsonPath("$[0].lastName").value(LAST_NAME))
        .andReturn();
  }

  @Test
  @DisplayName("Find users by first and last name")
  void findUsersByFirstAndLastName() throws Exception {
    Mockito.when(userRepository.save(USERDTO)).thenReturn(USERDTO);

    mockMvc
        .perform(
            post("/users")
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(getUserDtoString(USERDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
        .andExpect(jsonPath("$.lastName").value(LAST_NAME))
        .andReturn();

    Mockito.when(userRepository.find(FIRST_NAME, LAST_NAME)).thenReturn(Optional.of(USERDTO));

    mockMvc
        .perform(
            get("/users/find/{firstName}/{lastName}", FIRST_NAME, LAST_NAME)
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
        .andExpect(jsonPath("$.lastName").value(LAST_NAME))
        .andReturn();
  }

  @Test
  @DisplayName("Find all users")
  void findAllUsers() throws Exception {
    Mockito.when(userRepository.save(USERDTO)).thenReturn(USERDTO);

    mockMvc
        .perform(
            post("/users")
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(getUserDtoString(USERDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
        .andExpect(jsonPath("$.lastName").value(LAST_NAME))
        .andReturn();

    Mockito.when(userRepository.findAll()).thenReturn(Collections.singletonList(USERDTO));

    mockMvc
        .perform(
            get("/users/find-all")
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].firstName").value(FIRST_NAME))
        .andExpect(jsonPath("$[0].lastName").value(LAST_NAME))
        .andReturn();
  }

  @Test
  @DisplayName("Find users by last name not found")
  void findUsersNotFound() throws Exception {
    Mockito.when(userRepository.findAllByFirstName("RRR")).thenReturn(new ArrayList<>());

    mockMvc
        .perform(
            get("/users/find-all-by-first-name/{firstName}", "RRR")
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(
            result ->
                Assertions.assertTrue(
                    result.getResolvedException() instanceof UserNotFoundException))
        .andReturn();
  }

  @Test
  @DisplayName("Delete user that doesn't exist")
  void deleteUserDoesNotExist() throws Exception {
    Mockito.when(userRepository.delete(new UserDto("User", "Name")))
        .thenThrow(UserNotFoundException.class);

    mockMvc
        .perform(
            delete("/users/{firstName}/{lastName}", "User", "Name")
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(
            result ->
                Assertions.assertTrue(
                    result.getResolvedException() instanceof UserNotFoundException))
        .andReturn();
  }
}
