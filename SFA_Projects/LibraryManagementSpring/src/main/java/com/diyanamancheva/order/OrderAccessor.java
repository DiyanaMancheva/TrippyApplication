package com.diyanamancheva.order;

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
public class OrderAccessor {
    private final OrderMapper orderMapper;

    @Autowired
    public OrderAccessor(OrderMapper orderMapper) {
      this.orderMapper = orderMapper;
    }

    public void addOrder(Order order) {
      final String insertSQL = "INSERT INTO Orders(client_id, book_id, issue_date, due_date) VALUES(?,?,?)";

      try (Connection connection = DataSource.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

        preparedStatement.setInt(1, order.getClientId());
        preparedStatement.setInt(2, order.getBookId());
        preparedStatement.setString(3, order.getIssueDate());
        preparedStatement.setString(4, order.getDueDate());

        preparedStatement.executeUpdate();

        System.out.println("Order: " + order.getId() + " was successfully added.");
      } catch (SQLException e) {
        System.out.println("Order: " + order.getId() + " was NOT added.");
        throw new RuntimeException(e);
      }
    }

    public List<Order> readAllOrders() {
      ResultSet resultSet;
      List<Order> orders;
      try (Connection connection = DataSource.getConnection(); Statement statement = connection.createStatement()) {
        resultSet = statement.executeQuery("SELECT * FROM orders");
        orders = orderMapper.mapResultSetToOrders(resultSet);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      return orders;
    }

    public int updateOrder(Order order) {
      final String UpdateSQL = "UPDATE orders SET due_date = ? WHERE order_id = ?";
      try (Connection connection = DataSource.getConnection();
           PreparedStatement updateStatement = connection.prepareStatement(UpdateSQL)) {

        updateStatement.setString(1, order.getDueDate());
        updateStatement.setInt(2, order.getId());

        return updateStatement.executeUpdate();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }

    public int deleteOrder(int id) {
      final String deleteSQL = "DELETE FROM orders WHERE order_id = ?";

      try (Connection connection = DataSource.getConnection();
           PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {

        deleteStatement.setInt(1, id);
        return deleteStatement.executeUpdate();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
}
