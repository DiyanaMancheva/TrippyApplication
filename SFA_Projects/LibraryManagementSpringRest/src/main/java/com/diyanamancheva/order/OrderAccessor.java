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

    public List<Order> readAllOrders() {
      ResultSet resultSet;
      List<Order> orders;
      try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
        resultSet = statement.executeQuery("SELECT * FROM orders");
        orders = orderMapper.mapResultSetToOrders(resultSet);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      return orders;
    }

    public int updateOrder(Order order) {
      final String UpdateSQL = "UPDATE orders SET due_date = ? WHERE order_id = ?";
      try (Connection connection = dataSource.getConnection();
           PreparedStatement updateStatement = connection.prepareStatement(UpdateSQL)) {

        updateStatement.setDate(1, Date.valueOf(order.getDueDate().toString()));
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
