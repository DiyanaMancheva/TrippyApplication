package com.diyanamancheva.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

  public void addOrder(int clientId, int bookId, LocalDate issueDate) {
    Order order = new Order(clientId, bookId, issueDate);
    orderAccessor.addOrder(order);
  }

  public int editOrder(LocalDate dueDate, int id) {
    List<Order> orders = getAllOrders();
    Order orderToEdit = getOrderById(id, orders);
    orderToEdit.setDueDate(dueDate);
    return orderAccessor.updateOrder(orderToEdit);
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

  public List<Order> getOrderByClientId(int clientId, List<Order> orders) {
    List<Order> ordersByClientId = new ArrayList<Order>();

    for (Order orderFromList : orders) {
     if (orderFromList.getClientId() == clientId) {
        ordersByClientId.add(orderFromList);
      }
    }
    return ordersByClientId;
  }

  public List<Order> getOrderByBookId(int bookId, List<Order> orders) {
    List<Order> ordersByBookId = new ArrayList<Order>();

    for (Order orderFromList : orders) {
      if (orderFromList.getBookId() == bookId) {
        ordersByBookId.add(orderFromList);
      }
    }
    return ordersByBookId;
  }

  public List<Order> getOrderOnIssueDate(LocalDate issueDate, List<Order> orders) {
    List<Order> ordersOnIssueDate = new ArrayList<Order>();

    for (Order orderFromList : orders) {
      if ( orderFromList.getIssueDate().isEqual(issueDate)) {
        ordersOnIssueDate.add(orderFromList);
      }
    }
    return ordersOnIssueDate;
  }

  public List<Order> getOrderBeforeIssueDate(LocalDate issueDate, List<Order> orders) {
    List<Order> ordersBeforeIssueDate = new ArrayList<Order>();

    for (Order orderFromList : orders) {
      if ( orderFromList.getIssueDate().isBefore(issueDate)) {
        ordersBeforeIssueDate.add(orderFromList);
      }
    }
    return ordersBeforeIssueDate;
  }

  public List<Order> getOrderAfterIssueDate(LocalDate issueDate, List<Order> orders) {
    List<Order> ordersAfterIssueDate = new ArrayList<Order>();

    for (Order orderFromList : orders) {
      if ( orderFromList.getIssueDate().isAfter(issueDate)) {
        ordersAfterIssueDate.add(orderFromList);
      }
    }
    return ordersAfterIssueDate;
  }

  public List<Order> getOrderOnDueDate(LocalDate dueDate, List<Order> orders) {
    List<Order> ordersOnDueDate = new ArrayList<Order>();

    for (Order orderFromList : orders) {
      if ( orderFromList.getDueDate().isEqual(dueDate)) {
        ordersOnDueDate.add(orderFromList);
      }
    }
    return ordersOnDueDate;
  }

  public List<Order> getOrderBeforeDueDate(LocalDate dueDate, List<Order> orders) {
    List<Order> ordersBeforeDueDate = new ArrayList<Order>();

    for (Order orderFromList : orders) {
      if ( orderFromList.getDueDate().isBefore(dueDate)) {
        ordersBeforeDueDate.add(orderFromList);
      }
    }
    return ordersBeforeDueDate;
  }

  public List<Order> getOrderAfterDueDate(LocalDate dueDate, List<Order> orders) {
    List<Order> ordersAfterDueDate = new ArrayList<Order>();

    for (Order orderFromList : orders) {
      if ( orderFromList.getDueDate().isAfter(dueDate)) {
        ordersAfterDueDate.add(orderFromList);
      }
    }
    return ordersAfterDueDate;
  }
}
