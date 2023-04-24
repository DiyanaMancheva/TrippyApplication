package com.diyanamancheva.order;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class OrderRequest {
  @Positive(message = "ClientId must be greater than 0")
  private int clientId;
  @Positive(message = "BookId must be greater than 0")
  private int bookId;
  private LocalDate issueDate;

  public OrderRequest(int clientId, int bookId, LocalDate issueDate) {
    this.clientId = clientId;
    this.bookId = bookId;
    this.issueDate = issueDate;
  }

  public int getClientId() {
    return clientId;
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
    return issueDate;
  }

  public void setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
  }
}
