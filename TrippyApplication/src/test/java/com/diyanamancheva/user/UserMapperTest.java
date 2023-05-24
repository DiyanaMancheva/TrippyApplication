package com.diyanamancheva.user;

import com.diyanamancheva.dto.mapper.UserMapper;
import com.diyanamancheva.model.City;
import com.diyanamancheva.dto.user.UserDto;
import com.diyanamancheva.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {
  private static final int USER_ID = 1;
  private static final String USERNAME = "Test";
  private static final int CITY_ID = 1;
  private static final String CITY_NAME = "TestCity";
  private static final String EMAIL = "test.test@gmail.com";
  private static final LocalDate JOINDATE = LocalDate.parse("2023-05-01");

  @Mock
  private ResultSet resultSet;

  @InjectMocks
  private UserMapper userMapper;

  @Test
  public void mapUsersToDtos_noExceptions_success() {
    City cityTest = new City(CITY_ID, CITY_NAME);
    List<User> input = Collections.singletonList(new User(USER_ID, USERNAME, cityTest, EMAIL, JOINDATE));

    List<UserDto> result = userMapper.mapUsersToDtos(input);

    assertEquals(USERNAME, result.get(0).getUsername());
  }
}
