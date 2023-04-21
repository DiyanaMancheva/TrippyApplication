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

    public Client addClient(Client client) {
      final String insertSQL = "INSERT INTO Clients(client_name) VALUES(?)";
      int clientId;

      try (Connection connection = dataSource.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(insertSQL,
                                                                             Statement.RETURN_GENERATED_KEYS)) {

        preparedStatement.setString(1, client.getName());

        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if(resultSet.next()){
          clientId = resultSet.getInt(1);
        }else{
          throw new SQLException("ID was NOT retrieved from inserted client.");
        }
      } catch (SQLException e) {
         throw new RuntimeException("NOT able to create database connection.",e);
      }

      client.setId(clientId);
      return client;
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

    public Client readClientById(int id) {
      ResultSet resultSet;
      List<Client> clients;

      final String SQL = "SELECT * FROM clients WHERE client_id = ?";
      try (Connection connection = dataSource.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

        preparedStatement.setInt(1, id);

        resultSet = preparedStatement.executeQuery();

        clients = clientMapper.mapResultSetToClients(resultSet);
        if(clients.size() > 1){
          throw new SQLException("More than one clients with equal id");
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      return clients.get(0);
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
}
