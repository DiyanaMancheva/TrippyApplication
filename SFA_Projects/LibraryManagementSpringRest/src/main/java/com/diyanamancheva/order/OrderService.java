package com.diyanamancheva.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderService {
  private OrderAccessor orderAccessor;
  private OrderMapper orderMapper;

  @Autowired
  public OrderService(OrderAccessor orderAccessor, OrderMapper orderMapper) {
    this.orderAccessor = orderAccessor;
    this.orderMapper = orderMapper;
  }

  public Order getOrderById(int id){
    return orderAccessor.readOrderById(id);
  }

  public List<Order> getOrderByClient(int clientId){
    return orderAccessor.readOrderByClient(clientId);
  }

  public List<Order> getOrderByBook(int bookId){
    return orderAccessor.readOrderByBook(bookId);
  }

  public List<Order> getOrderByIssueDate(String issueDate){
    return orderAccessor.readOrderByIssueDate(issueDate);
  }

  public List<OrderDto> getAllOrders() {
    List<Order> orders = orderAccessor.readAllOrders();

    return orderMapper.mapOrdersToDtos(orders);
  }

  public void addOrder(int clientId, int bookId, LocalDate issueDate) {
    Order order = new Order(clientId, bookId, issueDate);
    orderAccessor.addOrder(order);
  }

  public OrderDto editOrder(int id, OrderRequest orderRequest){
    Order order = getOrderById(id);
    Order orderNew = new Order(id, orderRequest.getClientId(), orderRequest.getBookId(), orderRequest.getIssueDate());
    orderAccessor.updateOrder(orderNew);
    OrderDto orderDto = new OrderDto(order.getId(), order.getClientId(), order.getBookId(), order.getIssueDate());

    return orderDto;
  }

  public OrderDto deleteOrder(int id){
    Order orderOld = getOrderById(id);
    OrderDto orderDto = new OrderDto(orderOld.getId(), orderOld.getClientId(), orderOld.getBookId(), orderOld.getIssueDate());
    orderAccessor.deleteOrder(id);

    return orderDto;
  }

  public List<Order> getOrderOnIssueDate(LocalDate issueDate, List<Order> orders) {
    List<Order> ordersOnIssueDate = new ArrayList<>();

    for (Order orderFromList : orders) {
      if ( orderFromList.getIssueDate().isEqual(issueDate)) {
        ordersOnIssueDate.add(orderFromList);
      }
    }
    return ordersOnIssueDate;
  }

  public List<Order> getOrderBeforeIssueDate(LocalDate issueDate, List<Order> orders) {
    List<Order> ordersBeforeIssueDate = new ArrayList<>();

    for (Order orderFromList : orders) {
      if ( orderFromList.getIssueDate().isBefore(issueDate)) {
        ordersBeforeIssueDate.add(orderFromList);
      }
    }
    return ordersBeforeIssueDate;
  }

  public List<Order> getOrderAfterIssueDate(LocalDate issueDate, List<Order> orders) {
    List<Order> ordersAfterIssueDate = new ArrayList<>();

    for (Order orderFromList : orders) {
      if ( orderFromList.getIssueDate().isAfter(issueDate)) {
        ordersAfterIssueDate.add(orderFromList);
      }
    }
    return ordersAfterIssueDate;
  }

  public List<Order> getOrderOnDueDate(LocalDate dueDate, List<Order> orders) {
    List<Order> ordersOnDueDate = new ArrayList<>();

    for (Order orderFromList : orders) {
      if ( orderFromList.getDueDate().isEqual(dueDate)) {
        ordersOnDueDate.add(orderFromList);
      }
    }
    return ordersOnDueDate;
  }

  public List<Order> getOrderBeforeDueDate(LocalDate dueDate, List<Order> orders) {
    List<Order> ordersBeforeDueDate = new ArrayList<>();

    for (Order orderFromList : orders) {
      if ( orderFromList.getDueDate().isBefore(dueDate)) {
        ordersBeforeDueDate.add(orderFromList);
      }
    }
    return ordersBeforeDueDate;
  }

  public List<Order> getOrderAfterDueDate(LocalDate dueDate, List<Order> orders) {
    List<Order> ordersAfterDueDate = new ArrayList<>();

    for (Order orderFromList : orders) {
      if ( orderFromList.getDueDate().isAfter(dueDate)) {
        ordersAfterDueDate.add(orderFromList);
      }
    }
    return ordersAfterDueDate;
  }
}
