package com.diyanamancheva.order;

import java.time.LocalDate;

public class Order {
  private int id;
  private int clientId;
  private int bookId;
  private String issueDate;
  private final String dueDate;

  public Order(int clientId, int bookId, String issueDate){
    this.clientId = clientId;
    this.bookId = bookId;
    this.issueDate = issueDate;
    this.dueDate = CalculateDueDate();
  }

  public int getId(){ return this.id; }

  public void setId(int id){ this.id = id; }

  public int getClientId(){ return this.clientId; }

  public void setClientId(int clientId) { this.clientId = clientId; }

  public int getBookId() { return bookId; }

  public void setBookId(int bookId){ this.bookId = bookId; }

  public String getIssueDate(){ return this.issueDate; }

  public void setIssueDate(String issueDate){ this.issueDate = issueDate; }

  public String getDueDate(){ return this.dueDate; }

  private String CalculateDueDate(){
    return LocalDate.parse(this.issueDate).plusMonths(1).toString();
  }
}
