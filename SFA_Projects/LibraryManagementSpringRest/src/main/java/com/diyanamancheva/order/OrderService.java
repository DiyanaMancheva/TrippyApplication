package com.diyanamancheva.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

  public List<Order> getOrderByDueDate(String dueDate){
    return orderAccessor.readOrderByDueDate(dueDate);
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
}
