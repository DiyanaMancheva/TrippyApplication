package com.diyanamancheva.order;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

  public List<Order> mapResultSetToOrders(ResultSet ordersResultSet) {
    List<Order> ordersList = new ArrayList<>();
    try (ordersResultSet) {
      while (ordersResultSet.next()) {
        int id = ordersResultSet.getInt(1);
        int clientId = ordersResultSet.getInt(2);
        int bookId = ordersResultSet.getInt(3);
        String issueDate = ordersResultSet.getString(4);
        String dueDate = ordersResultSet.getString(5);


        Order order = new Order(clientId, bookId, issueDate);

        order.setId(id);
        ordersList.add(order);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return ordersList;
  }
}
