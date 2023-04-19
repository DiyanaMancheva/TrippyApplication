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

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {
  @Mock
  private ClientAccessor clientAccessor;
  @InjectMocks
  private ClientService clientService;
  @Test
  public void testGetAllClients(){
    when(clientAccessor.readAllClients()).thenReturn(Collections.singletonList(new Client("Ivan Ivanov")));

    List<ClientDto> clients = clientService.getAllClients();

    Assert.assertEquals("Ivan Ivanov", clients.get(0).getName());
  }
}
