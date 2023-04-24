package com.diyanamancheva.order;

import com.diyanamancheva.exception.DatabaseConnectivityException;
import com.diyanamancheva.exception.IDNotUniqueException;
import com.diyanamancheva.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class OrderAccessor {
    private OrderMapper orderMapper;
    private HikariDataSource dataSource;

    @Autowired
    public OrderAccessor(OrderMapper orderMapper, HikariDataSource dataSource) {
      this.orderMapper = orderMapper;
      this.dataSource = dataSource;
    }

    public Order readOrderById(int id){
    ResultSet resultSet;
    List<Order> orders;

    final String selectSQL = "SELECT * FROM orders WHERE order_id = ?";

    try(Connection connection = dataSource.getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

      preparedStatement.setInt(1, id);

      resultSet = preparedStatement.executeQuery();

      orders = orderMapper.mapResultSetToOrders(resultSet);

      if(orders.size() > 1){
        throw new IDNotUniqueException(String.format("More than one orders with equal id = %d found.", id));
      }else if (orders.size() == 0){
        throw new ItemNotFoundException(String.format("No orders with id %d found ", id));
      }
    }catch (SQLException e){
      throw new DatabaseConnectivityException(e);
    }

    return orders.get(0);
    }

    public List<Order> readOrderByClient(int clientId){
      ResultSet resultSet;
      List<Order> orders;


      final String selectSQL = "SELECT * FROM orders WHERE client_id = ?";

      try(Connection connection = dataSource.getConnection();
          PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

        preparedStatement.setInt(1, clientId);

        resultSet = preparedStatement.executeQuery();

        orders = orderMapper.mapResultSetToOrders(resultSet);

        if(orders.size() == 0) {
          throw new ItemNotFoundException(String.format("No orders with clientId %d found ", clientId));
        }
      }catch (SQLException e){
        throw new DatabaseConnectivityException(e);
      }

      return orders;
    }

    public List<Order> readOrderByBook(int bookId){
      ResultSet resultSet;
      List<Order> orders;


      final String selectSQL = "SELECT * FROM orders WHERE book_id = ?";

      try(Connection connection = dataSource.getConnection();
          PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

        preparedStatement.setInt(1, bookId);

        resultSet = preparedStatement.executeQuery();

        orders = orderMapper.mapResultSetToOrders(resultSet);

        if(orders.size() == 0) {
          throw new ItemNotFoundException(String.format("No orders with bookId %d found ", bookId));
        }
      }catch (SQLException e){
        throw new DatabaseConnectivityException(e);
      }

      return orders;
    }

    public List<Order> readOrderByIssueDate(String issueDate){
      ResultSet resultSet;
      List<Order> orders;

      final String selectSQL = "SELECT * FROM orders WHERE issue_date = ?";

      try(Connection connection = dataSource.getConnection();
          PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

        preparedStatement.setDate(1, Date.valueOf(issueDate));

        resultSet = preparedStatement.executeQuery();

        orders = orderMapper.mapResultSetToOrders(resultSet);

        if(orders.size() == 0) {
          throw new ItemNotFoundException("No orders with issueDate " + issueDate + " found ");
        }
      }catch (SQLException e){
        throw new DatabaseConnectivityException(e);
      }

      return orders;
    }

    public List<Order> readOrderByDueDate(String dueDate){
      ResultSet resultSet;
      List<Order> orders;

      final String selectSQL = "SELECT * FROM orders WHERE due_date = ?";

      try(Connection connection = dataSource.getConnection();
          PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

        preparedStatement.setDate(1, Date.valueOf(dueDate));
  
        resultSet = preparedStatement.executeQuery();

        orders = orderMapper.mapResultSetToOrders(resultSet);

        if(orders.size() == 0) {
          throw new ItemNotFoundException("No orders with dueDate " + dueDate + " found ");
        }
      }catch (SQLException e){
        throw new DatabaseConnectivityException(e);
      }

      return orders;
    }

    public List<Order> readAllOrders() {
      ResultSet resultSet;
      List<Order> orders;
      final String selectSQL = "SELECT * FROM orders";

      try (Connection connection = dataSource.getConnection();
           Statement statement = connection.createStatement()) {

        resultSet = statement.executeQuery(selectSQL);
        orders = orderMapper.mapResultSetToOrders(resultSet);
        if(orders.size() == 0) {
          throw new ItemNotFoundException("No orders found.");
        }
      } catch (SQLException e) {
          throw new DatabaseConnectivityException(e);
      }
      return orders;
    }

    public void addOrder(Order order) {
      final String insertSQL = "INSERT INTO Orders(client_id, book_id, issue_date, due_date) VALUES(?,?,?,?)";

      try (Connection connection = dataSource.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

        preparedStatement.setInt(1, order.getClientId());
        preparedStatement.setInt(2, order.getBookId());
        preparedStatement.setDate(3, Date.valueOf(order.getIssueDate().toString()));
        preparedStatement.setDate(4, Date.valueOf(order.getDueDate().toString()));

        preparedStatement.executeUpdate();
      } catch (SQLException e) {
          throw new DatabaseConnectivityException(e);
      }
    }

    public int updateOrder(Order order) {
      final String UpdateSQL = "UPDATE orders SET book_id = ? WHERE order_id = ?";

      try (Connection connection = dataSource.getConnection();
           PreparedStatement updateStatement = connection.prepareStatement(UpdateSQL)) {

        updateStatement.setInt(1, order.getBookId());
        updateStatement.setInt(2, order.getId());

        return updateStatement.executeUpdate();
      } catch (SQLException e) {
          throw new DatabaseConnectivityException(e);
      }
    }

    public int deleteOrder(int id) {
      final String deleteSQL = "DELETE FROM orders WHERE order_id = ?";

      try (Connection connection = dataSource.getConnection();
           PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {

        deleteStatement.setInt(1, id);

        return deleteStatement.executeUpdate();
      } catch (SQLException e) {
          throw new DatabaseConnectivityException(e);
      }
    }
}
