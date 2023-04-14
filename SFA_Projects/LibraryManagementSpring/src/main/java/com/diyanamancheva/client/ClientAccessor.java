package com.diyanamancheva.client;

import com.diyanamancheva.author.Author;
import com.diyanamancheva.author.AuthorMapper;
import com.diyanamancheva.configuration.DataSource;
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

    private static final String FILE_NOT_FOUND_MESSAGE = "File not found with path ";

    private final ClientMapper clientMapper;

    @Autowired
    public ClientAccessor(ClientMapper clientMapper) {
      this.clientMapper = clientMapper;
    }

    public void addClient(Client client) {
      final String insertSQL = "INSERT INTO Clients(client_name) VALUES(?)";

      try (Connection connection = DataSource.getConnection();
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
      try (Connection connection = DataSource.getConnection(); Statement statement = connection.createStatement()) {
        resultSet = statement.executeQuery("SELECT * FROM clients");
        clients = clientMapper.mapResultSetToClients(resultSet);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      return clients;
    }

    public int updateClient(Client client) {
      final String UpdateSQL = "UPDATE clients SET client_name = ? WHERE client_id = ?";
      try (Connection connection = DataSource.getConnection();
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

      try (Connection connection = DataSource.getConnection();
           PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {

        deleteStatement.setInt(1, id);
        return deleteStatement.executeUpdate();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
}
