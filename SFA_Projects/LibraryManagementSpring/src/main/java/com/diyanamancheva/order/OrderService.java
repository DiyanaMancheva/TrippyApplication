package com.diyanamancheva.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
  private final OrderAccessor orderAccessor;

  @Autowired
  public OrderService(OrderAccessor orderAccessor) {
    this.orderAccessor = orderAccessor;
  }

  public List<Order> getAllOrders() {
    return orderAccessor.readAllOrders();
  }

  public void addOrder(int clientId, int bookId, String issueDate) {
    Order order = new Order(clientId, bookId, issueDate);
    orderAccessor.addOrder(order);
  }

  public int editOrder(int dueDate, int id) {
    List<Order> orders = getAllOrders();
    Order order = getOrderById(id, orders);
    return orderAccessor.updateOrder(order);
  }

  public int deleteOrder(int id) { return orderAccessor.deleteOrder(id); }

  public Order getOrderById(int id, List<Order> orders) {
    Order order = null;
    for (Order orderFromList : orders) {
      if (orderFromList.getId() == id) {
        order = orderFromList;
        break;
      }
    }
    return order;
  }

  public Order getOrderByClientId(int clientId, List<Order> orders) {
    Order order = null;
    for (Order orderFromList : orders) {
      if (orderFromList.getClientId() == clientId) {
        order = orderFromList;
        break;
      }
    }
    return order;
  }

  public Order getOrderByBookId(int bookId, List<Order> orders) {
    Order order = null;
    for (Order orderFromList : orders) {
      if (orderFromList.getBookId() == bookId) {
        order = orderFromList;
        break;
      }
    }
    return order;
  }

  public Order getOrderOnIssueDate(String issueDate, List<Order> orders) {
    Order order = null;
    for (Order orderFromList : orders) {
      LocalDate orderFromListLocal = LocalDate.parse(orderFromList.getIssueDate());
      LocalDate issueLocal = LocalDate.parse(issueDate);
      if ( orderFromListLocal.isEqual(issueLocal)) {
        order = orderFromList;
        break;
      }
    }
    return order;
  }

  public Order getOrderBeforeIssueDate(String issueDate, List<Order> orders) {
    Order order = null;
    for (Order orderFromList : orders) {
      LocalDate orderFromListLocal = LocalDate.parse(orderFromList.getIssueDate());
      LocalDate issueLocal = LocalDate.parse(issueDate);
      if ( orderFromListLocal.isBefore(issueLocal)) {
        order = orderFromList;
        break;
      }
    }
    return order;
  }

  public Order getOrderAfterIssueDate(String issueDate, List<Order> orders) {
    Order order = null;
    for (Order orderFromList : orders) {
      LocalDate orderFromListLocal = LocalDate.parse(orderFromList.getIssueDate());
      LocalDate issueLocal = LocalDate.parse(issueDate);
      if ( orderFromListLocal.isAfter(issueLocal)) {
        order = orderFromList;
        break;
      }
    }
    return order;
  }

  public Order getOrderOnDueDate(String dueDate, List<Order> orders) {
    Order order = null;
    for (Order orderFromList : orders) {
      LocalDate orderFromListLocal = LocalDate.parse(orderFromList.getDueDate());
      LocalDate dueLocal = LocalDate.parse(dueDate);
      if ( orderFromListLocal.isEqual(dueLocal)) {
        order = orderFromList;
        break;
      }
    }
    return order;
  }

  public Order getOrderBeforeDueDate(String dueDate, List<Order> orders) {
    Order order = null;
    for (Order orderFromList : orders) {
      LocalDate orderFromListLocal = LocalDate.parse(orderFromList.getDueDate());
      LocalDate dueLocal = LocalDate.parse(dueDate);
      if ( orderFromListLocal.isBefore(dueLocal)) {
        order = orderFromList;
        break;
      }
    }
    return order;
  }

  public Order getOrderAfterDueDate(String dueDate, List<Order> orders) {
    Order order = null;
    for (Order orderFromList : orders) {
      LocalDate orderFromListLocal = LocalDate.parse(orderFromList.getDueDate());
      LocalDate dueLocal = LocalDate.parse(dueDate);
      if ( orderFromListLocal.isAfter(dueLocal)) {
        order = orderFromList;
        break;
      }
    }
    return order;
  }
}
