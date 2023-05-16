package com.diyanamancheva.user;

import com.diyanamancheva.controller.UserController;
import com.diyanamancheva.controller.request.user.UserUpdateRequest;
import com.diyanamancheva.dto.user.UserDto;
import com.diyanamancheva.model.City;
import com.diyanamancheva.model.User;
import com.diyanamancheva.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
  private static final int USER_ID = 1;
  private static final String USERNAME = "Test";
  private static final int CITY_ID = 1;
  private static final String CITY_NAME = "TestCity";
  private static final String EMAIL = "test.test@gmail.com";
  private static final LocalDate JOINDATE = LocalDate.parse("2023-05-01");

  private MockMvc mockMvc;

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @Test
  public void getAllUsers_singleUser_success() throws Exception {
    City cityTest = new City(CITY_ID, CITY_NAME);
    when(userService.getAllUsers()).thenReturn(Collections.singletonList(new UserDto(USER_ID, USERNAME, cityTest,
                                                                                     EMAIL, LocalDate.parse("2023-05-01"))));

    mockMvc.perform(get("/users"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(USER_ID))
           .andExpect(jsonPath("$[0].username").value(USERNAME))
           .andExpect(jsonPath("$[0].city.id").value(CITY_ID))
           .andExpect(jsonPath("$[0].city.name").value(CITY_NAME))
           .andExpect(jsonPath("$[0].email").value(EMAIL))
           .andExpect(jsonPath("$[0].joinDate[0]").value("2023"))
           .andExpect(jsonPath("$[0].joinDate[1]").value("5"))
           .andExpect(jsonPath("$[0].joinDate[2]").value("1"));
  }

  @Test
  public void getAllUsers_emptyList_success() throws Exception {
    when(userService.getAllUsers()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/users"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").exists())
           .andExpect(jsonPath("$[0]").doesNotExist());
  }

  @Test
  public void getUserById_success() throws Exception {
    City cityTest = new City(CITY_ID, CITY_NAME);
    when(userService.getUserById(USER_ID)).thenReturn(new User(USER_ID, USERNAME, cityTest,
                                                               EMAIL, LocalDate.parse("2023-05-01")));

    mockMvc.perform(get("/users/" + USER_ID))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(USER_ID))
           .andExpect(jsonPath("$.username").value(USERNAME))
           .andExpect(jsonPath("$.city.id").value(CITY_ID))
           .andExpect(jsonPath("$.city.name").value(CITY_NAME))
           .andExpect(jsonPath("$.email").value(EMAIL))
           .andExpect(jsonPath("$.joinDate[0]").value("2023"))
           .andExpect(jsonPath("$.joinDate[1]").value("5"))
           .andExpect(jsonPath("$.joinDate[2]").value("1"));
  }

  @Test
  public void updateUser_noResponse_success() throws Exception {
    City cityTest = new City(CITY_ID, CITY_NAME);
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(new UserUpdateRequest(USERNAME, CITY_ID,
                                                                  EMAIL));

    when(userService.updateUser(eq(USER_ID), any())).thenReturn(new UserDto(USER_ID, USERNAME,
                                                                            cityTest, EMAIL, JOINDATE));

    mockMvc.perform(put("/users/" + USER_ID)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json))
           .andExpect(status().isNoContent());
  }

  @Test
  public void updateUser_requestedResponse_success() throws Exception {
    City cityTest = new City(CITY_ID, CITY_NAME);
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(new UserUpdateRequest(USERNAME, CITY_ID, EMAIL));

    when(userService.updateUser(eq(USER_ID), any())).thenReturn(new UserDto(USER_ID, USERNAME, cityTest, EMAIL, JOINDATE));

    mockMvc.perform(put("/users/" + USER_ID)
                      .queryParam("returnOld", "true")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(USER_ID))
           .andExpect(jsonPath("$.username").value(USERNAME))
           .andExpect(jsonPath("$.city.id").value(CITY_ID))
           .andExpect(jsonPath("$.city.name").value(CITY_NAME))
           .andExpect(jsonPath("$.email").value(EMAIL))
           .andExpect(jsonPath("$.joinDate[0]").value("2023"))
           .andExpect(jsonPath("$.joinDate[1]").value("5"))
           .andExpect(jsonPath("$.joinDate[2]").value("1"));
  }

  @Test
  public void deleteUser_noResponse_success() throws Exception {
    City cityTest = new City(CITY_ID, CITY_NAME);
    when(userService.deleteUser(USER_ID)).thenReturn(new UserDto(USER_ID, USERNAME, cityTest,
                                                                 EMAIL, JOINDATE));
    mockMvc.perform(delete("/users/" + USER_ID))
           .andExpect(status().isNoContent());
  }

  @Test
  public void deleteCity_requestedResponse_success() throws Exception {
    City cityTest = new City(CITY_ID, CITY_NAME);
    when(userService.deleteUser(USER_ID)).thenReturn(new UserDto(USER_ID, USERNAME, cityTest,
                                                                 EMAIL, JOINDATE));
    mockMvc.perform(delete("/users/" + USER_ID)
                      .queryParam("returnOld", "true"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(USER_ID))
           .andExpect(jsonPath("$.username").value(USERNAME))
           .andExpect(jsonPath("$.city.id").value(CITY_ID))
           .andExpect(jsonPath("$.city.name").value(CITY_NAME))
           .andExpect(jsonPath("$.email").value(EMAIL))
           .andExpect(jsonPath("$.joinDate[0]").value("2023"))
           .andExpect(jsonPath("$.joinDate[1]").value("5"))
           .andExpect(jsonPath("$.joinDate[2]").value("1"));
  }
}
