package com.diyanamancheva.client;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
//import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ClientAccessorTest {
  @Mock
  private ClientAccessor clientAccessor;
  @InjectMocks
  private ClientService clientService;
  @Test
  public void testGetAllClients(){
    when(clientAccessor.readAllClients()).thenReturn(Collections.singletonList(new Client("Ivan Ivanov")));

    List<Client> clients = clientService.getAllClients();

    Assert.assertEquals("Ivan Ivanov", clients.get(0).getName());
  }
}
