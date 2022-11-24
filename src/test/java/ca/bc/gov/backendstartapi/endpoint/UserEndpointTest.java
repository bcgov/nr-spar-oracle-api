package ca.bc.gov.backendstartapi.endpoint;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class UserEndpointTest {

  private static final String FIRST_NAME = "Ricardo";
  private static final String LAST_NAME = "Campos";
  private static final UserDto USERDTO = new UserDto(FIRST_NAME, LAST_NAME);

  private MockMvc mockMvc;

  @Autowired UserRepository userRepository;

  @Autowired private WebApplicationContext webApplicationContext;

  @BeforeEach
  public void setup() {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
  }

  private String getUserDtoString(UserDto userDto) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(userDto);
  }

  @Test
  @Order(1)
  @DisplayName("Create user with success")
  @WithMockUser(roles = "user_write")
  void createSuccess() throws Exception {
    mockMvc
        .perform(
            post("/api/users")
                .with(csrf().asHeader())
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(getUserDtoString(USERDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
        .andExpect(jsonPath("$.lastName").value(LAST_NAME))
        .andReturn();
  }

  @Test
  @Order(2)
  @DisplayName("Create user without firstName")
  @WithMockUser(roles = "user_write")
  void createWithoutFirstName() throws Exception {
    UserDto userDtoPartial = new UserDto(null, LAST_NAME);

    mockMvc
        .perform(
            post("/api/users")
                .with(csrf().asHeader())
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(getUserDtoString(userDtoPartial)))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("$.errorMessage").value("1 field(s) with validation problems!"))
        .andExpect(jsonPath("$.fields[0].fieldName").value("firstName"))
        .andExpect(jsonPath("$.fields[0].fieldMessage").value("must not be blank"))
        .andReturn();
  }

  @Test
  @Order(3)
  @DisplayName("Create user without lastName")
  @WithMockUser(roles = "user_write")
  void createWithoutLastName() throws Exception {
    UserDto userDtoPartial = new UserDto(FIRST_NAME, null);

    mockMvc
        .perform(
            post("/api/users")
                .with(csrf().asHeader())
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(getUserDtoString(userDtoPartial)))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("$.errorMessage").value("1 field(s) with validation problems!"))
        .andExpect(jsonPath("$.fields[0].fieldName").value("lastName"))
        .andExpect(jsonPath("$.fields[0].fieldMessage").value("must not be blank"))
        .andReturn();
  }

  @Test
  @Order(4)
  @DisplayName("Create user with bellow minimum lastName size")
  @WithMockUser(roles = "user_write")
  void createSizeMin() throws Exception {
    UserDto userDtoError = new UserDto(FIRST_NAME, "C");

    mockMvc
        .perform(
            post("/api/users")
                .with(csrf().asHeader())
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(getUserDtoString(userDtoError)))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("$.errorMessage").value("1 field(s) with validation problems!"))
        .andExpect(jsonPath("$.fields[0].fieldName").value("lastName"))
        .andExpect(jsonPath("$.fields[0].fieldMessage").value("size must be between 2 and 20"))
        .andReturn();
  }

  @Test
  @Order(5)
  @DisplayName("Create user with above than maximum lastName size")
  @WithMockUser(roles = "user_write")
  void createSizeMax() throws Exception {
    UserDto userDtoError = new UserDto("Ricardo", "CamposCamposCamposCampos");

    mockMvc
        .perform(
            post("/api/users")
                .with(csrf().asHeader())
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(getUserDtoString(userDtoError)))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("$.errorMessage").value("1 field(s) with validation problems!"))
        .andExpect(jsonPath("$.fields[0].fieldName").value("lastName"))
        .andExpect(jsonPath("$.fields[0].fieldMessage").value("size must be between 2 and 20"))
        .andReturn();
  }

  @Test
  @Order(6)
  @DisplayName("Try to create existing user")
  @WithMockUser(roles = "user_write")
  void createExisting() throws Exception {
    mockMvc
        .perform(
            post("/api/users")
                .with(csrf().asHeader())
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
  @Order(7)
  @DisplayName("Find users by first name")
  @WithMockUser(roles = "user_read")
  void findUsersByFirstName() throws Exception {
    mockMvc
        .perform(
            get("/api/users/find-all-by-first-name/{firstName}", FIRST_NAME)
                .with(csrf().asHeader())
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].firstName").value(FIRST_NAME))
        .andExpect(jsonPath("$[0].lastName").value(LAST_NAME))
        .andReturn();
  }

  @Test
  @Order(8)
  @DisplayName("Find users by last name")
  @WithMockUser(roles = "user_read")
  void findUsersByLastName() throws Exception {
    mockMvc
        .perform(
            get("/api/users/find-all-by-last-name/{lastName}", LAST_NAME)
                .with(csrf().asHeader())
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].firstName").value(FIRST_NAME))
        .andExpect(jsonPath("$[0].lastName").value(LAST_NAME))
        .andReturn();
  }

  @Test
  @Order(9)
  @DisplayName("Find users by first and last name - 403 Forbidden")
  @WithMockUser
  void findUsersByFirstAndLastNameUnauthorized() throws Exception {
    mockMvc
        .perform(
            get("/api/users/find/{firstName}/{lastName}", FIRST_NAME, LAST_NAME)
                .with(csrf().asHeader())
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.FORBIDDEN.value()))
        .andReturn();
  }

  @Test
  @Order(9)
  @DisplayName("Find users by first and last name - Authenticated")
  @WithMockUser(roles = "user_read")
  void findUsersByFirstAndLastName() throws Exception {
    mockMvc
        .perform(
            get("/api/users/find/{firstName}/{lastName}", FIRST_NAME, LAST_NAME)
                .with(csrf().asHeader())
                .header("Content-Type", "application/json")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
        .andExpect(jsonPath("$.lastName").value(LAST_NAME))
        .andReturn();
  }

  @Test
  @Order(10)
  @DisplayName("Find all users")
  @WithMockUser(roles = "user_read")
  void findAllUsers() throws Exception {
    mockMvc
        .perform(
            get("/api/users/find-all")
                .with(csrf().asHeader())
                .header("Content-Type", "application/json")
                .header("X-Authorization", "Bearer lalala")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].firstName").value(FIRST_NAME))
        .andExpect(jsonPath("$[0].lastName").value(LAST_NAME))
        .andReturn();
  }

  @Test
  @Order(11)
  @DisplayName("Find users by last name not found")
  @WithMockUser(roles = "user_read")
  void findUsersNotFound() throws Exception {
    mockMvc
        .perform(
            get("/api/users/find-all-by-first-name/{firstName}", "RRR")
                .with(csrf().asHeader())
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
  @Order(12)
  @DisplayName("Delete user that doesn't exist")
  @WithMockUser(roles = "user_write")
  void deleteUserDoesNotExist() throws Exception {
    mockMvc
        .perform(
            delete("/api/users/{firstName}/{lastName}", "User", "Name")
                .with(csrf().asHeader())
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
