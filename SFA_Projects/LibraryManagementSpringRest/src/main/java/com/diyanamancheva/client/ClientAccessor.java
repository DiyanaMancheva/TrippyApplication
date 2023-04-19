package com.diyanamancheva.client;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class ClientAccessor {
    private ClientMapper clientMapper;
    private HikariDataSource dataSource;

    @Autowired
    public ClientAccessor(ClientMapper clientMapper, HikariDataSource dataSource) {
      this.clientMapper = clientMapper;
      this.dataSource = dataSource;
    }

    public void addClient(Client client) {
      final String insertSQL = "INSERT INTO Clients(client_name) VALUES(?)";

      try (Connection connection = dataSource.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

        preparedStatement.setString(1, client.getName());

        preparedStatement.executeUpdate();

        System.out.println("Client: " + client.getName() + " was successfully added.");
      } catch (SQLException e) {
        System.out.println("Client: " + client.getName() + " was NOT added.");
        throw new RuntimeException(e);
      }
    }

    public List<Client> readAllClients() {
      ResultSet resultSet;
      List<Client> clients;
      try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
        resultSet = statement.executeQuery("SELECT * FROM clients");
        clients = clientMapper.mapResultSetToClients(resultSet);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      return clients;
    }

    public int updateClient(Client client) {
      final String UpdateSQL = "UPDATE clients SET client_name = ? WHERE client_id = ?";
      try (Connection connection = dataSource.getConnection();
           PreparedStatement updateStatement = connection.prepareStatement(UpdateSQL)) {

        updateStatement.setString(1, client.getName());
        updateStatement.setInt(2, client.getId());

        return updateStatement.executeUpdate();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }

    public int deleteClient(int id) {
      final String deleteSQL = "DELETE FROM clients WHERE client_id = ?";

      try (Connection connection = dataSource.getConnection();
           PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {

        deleteStatement.setInt(1, id);
        return deleteStatement.executeUpdate();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
}
