package com.diyanamancheva.client;

import com.diyanamancheva.exception.DatabaseConnectivityException;
import com.diyanamancheva.exception.IDNotUniqueException;
import com.diyanamancheva.exception.ItemNotFoundException;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(ClientAccessor.class);

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

        log.debug("Trying to persist new client");
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if(resultSet.next()){
          clientId = resultSet.getInt(1);
        }else{
          log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
          throw new SQLException("ID was NOT retrieved from inserted client.");
        }
      } catch (SQLException e) {
          log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
          throw new DatabaseConnectivityException(e);
      }

      client.setId(clientId);
      log.info(String.format("Client with id %d successfully persisted", clientId));
      return client;
    }

    public List<Client> readAllClients() {
      ResultSet resultSet;
      List<Client> clients;
      try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
        resultSet = statement.executeQuery("SELECT * FROM clients");
        clients = clientMapper.mapResultSetToClients(resultSet);
      } catch (SQLException e) {
          log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
          throw new DatabaseConnectivityException(e);
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
          log.error(String.format("More than one clients with equal id = %d found.", id));
          throw new IDNotUniqueException(String.format("More than one clients with equal id = %d found.", id));
        }else if (clients.size() == 0){
          log.error(String.format("No clients with id %d found", id));
          throw new ItemNotFoundException(String.format("No clients with id %d found", id));
        }
      } catch (SQLException e) {
          log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
          throw new DatabaseConnectivityException(e);
      }
      return clients.get(0);
    }

    public int updateClient(Client client) {
      final String updateSQL = "UPDATE clients SET client_name = ? WHERE client_id = ?";
      try (Connection connection = dataSource.getConnection();
           PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {

        updateStatement.setString(1, client.getName());
        updateStatement.setInt(2, client.getId());

        return updateStatement.executeUpdate();
      } catch (SQLException e) {
          log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
          throw new DatabaseConnectivityException(e);
      }
    }

    public int deleteClient(int id) {
      final String deleteSQL = "DELETE FROM clients WHERE client_id = ?";

      try (Connection connection = dataSource.getConnection();
           PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {

        deleteStatement.setInt(1, id);
        return deleteStatement.executeUpdate();
      } catch (SQLException e) {
          log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
          throw new DatabaseConnectivityException(e);
      }
    }
}
