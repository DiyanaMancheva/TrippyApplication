package com.diyanamancheva.user;

import com.diyanamancheva.city.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  @Mock
  private UserAccessor userAccessor;

  @InjectMocks
  private UserService userService;

  public static final int USER_ID = 1;
  public static final String USERNAME = "Test";
  public static final int CITY_ID = 1;
  public static final String CITY_NAME = "TestCity";
  public static final String EMAIL = "test.test@gmail.com";
  public static final LocalDate JOINDATE = LocalDate.parse("2023-05-01");

  @Test
  public void deleteUser_success() {
    City cityTest = new City(CITY_ID, CITY_NAME);
    when(userService.getUserById(USER_ID)).thenReturn(new User(USER_ID, USERNAME, cityTest, EMAIL, JOINDATE));

    userService.deleteUser(USER_ID);

    verify(userAccessor, times(1)).readUserById(USER_ID);
    verify(userAccessor, times(1)).deleteUser(USER_ID);
  }

}
