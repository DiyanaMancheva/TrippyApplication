package com.diyanamancheva.order;

import java.time.LocalDate;

  public class OrderDto {
    private int id;
    private int clientId;
    private int bookId;
    private LocalDate issueDate;
    private LocalDate dueDate;

    public OrderDto(int id, int clientId, int bookId, LocalDate issueDate) {
      this.id = id;
      this.clientId = clientId;
      this.bookId = bookId;
      this.issueDate = issueDate;
      this.dueDate = issueDate.plusMonths(1);
    }

    public int getId() {
      return id;
    }

    public void setId ( int id) {
      this.id = id;
    }
    public int getClientId () {
      return clientId;
    }

    public void setClientId ( int clientId){
      this.clientId = clientId;
    }

    public int getBookId () {
      return bookId;
    }

    public void setBookId ( int bookId){
      this.bookId = bookId;
    }

    public LocalDate getIssueDate () {
      return issueDate;
    }

    public void setIssueDate (LocalDate issueDate){
      this.issueDate = issueDate;
    }

    public LocalDate getDueDate () {
      return dueDate;
    }

    public void setDueDate (LocalDate dueDate){
      this.dueDate = dueDate;
    }
  }

