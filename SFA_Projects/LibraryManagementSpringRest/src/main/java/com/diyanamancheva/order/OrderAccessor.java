package com.diyanamancheva.order;

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

      if (orders.size() > 1){
        throw new SQLException("More than one orders with equal id.");
      }
    }catch (SQLException e){
      throw new RuntimeException(e);
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

        if (orders.size() == 0){
          throw new SQLException("No orders found.");
        }
      }catch (SQLException e){
        throw new RuntimeException(e);
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
      } catch (SQLException e) {
        throw new RuntimeException(e);
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
        throw new RuntimeException(e);
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
        throw new RuntimeException(e);
      }
    }

    public int deleteOrder(int id) {
      final String deleteSQL = "DELETE FROM orders WHERE order_id = ?";

      try (Connection connection = dataSource.getConnection();
           PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {

        deleteStatement.setInt(1, id);

        return deleteStatement.executeUpdate();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
}
