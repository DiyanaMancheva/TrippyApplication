package com.diyanamancheva.order;

import java.time.LocalDate;

public class Order {

  private int id;
  private int clientId;
  private int bookId;
  private LocalDate issueDate;
  private LocalDate dueDate;

  public Order(int clientId, int bookId, LocalDate issueDate) {
    this.clientId = clientId;
    this.bookId = bookId;
    this.issueDate = issueDate;
    this.dueDate = CalculateDueDate();
  }

  public Order(int id, int clientId, int bookId, LocalDate issueDate){
    this(clientId, bookId, issueDate);
    this.id = id;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getClientId() {
    return this.clientId;
  }

  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  public int getBookId() {
    return bookId;
  }

  public void setBookId(int bookId) {
    this.bookId = bookId;
  }

  public LocalDate getIssueDate() {
    return this.issueDate;
  }

  public void setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
  }

  public LocalDate getDueDate() {
    return this.dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  private LocalDate CalculateDueDate() {
    return this.issueDate.plusMonths(1);
  }
}
