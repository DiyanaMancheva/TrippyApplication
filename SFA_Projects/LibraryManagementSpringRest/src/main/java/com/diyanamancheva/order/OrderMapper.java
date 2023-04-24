package com.diyanamancheva.order;

import com.diyanamancheva.exception.DatabaseConnectivityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {
  private static final Logger log = LoggerFactory.getLogger(OrderAccessor.class);

  public List<Order> mapResultSetToOrders(ResultSet ordersResultSet) {
    List<Order> ordersList = new ArrayList<>();
    try (ordersResultSet) {
      while (ordersResultSet.next()) {
        int id = ordersResultSet.getInt(1);
        int clientId = ordersResultSet.getInt(2);
        int bookId = ordersResultSet.getInt(3);
        String issueDate = ordersResultSet.getString(4);
        LocalDate issueDateLocal = LocalDate.parse(issueDate);
        String dueDate = ordersResultSet.getString(5);
        LocalDate dueDateLocal = LocalDate.parse(dueDate);


        Order order = new Order(clientId, bookId, issueDateLocal);

        order.setId(id);
        order.setDueDate(dueDateLocal);

        ordersList.add(order);
      }
    } catch (SQLException e) {
      log.error("Unexpected exception occured when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }
    return ordersList;
  }

  public List<OrderDto> mapOrdersToDtos(List<Order> orders){
    ArrayList<OrderDto> orderDtos = new ArrayList<>();

    for(Order order : orders){
      OrderDto orderDto = new OrderDto(order.getId(), order.getClientId(), order.getBookId(), order.getIssueDate());
      orderDtos.add(orderDto);
    }
    return orderDtos;
  }
}
