package com.diyanamancheva.order;

import com.diyanamancheva.book.Book;
import com.diyanamancheva.client.Client;

import java.util.ArrayList;

public class Order {
  private int id;
  private Client client;
  private Book book;
  private String issueDate;
  private String dueDate;
  public Order(Book book, Client client, String issueDate){
    this.book = book;
    this.client = client;
    this.issueDate = issueDate;
    this.dueDate = setDueDate();
    this.book.isAvailable = false;
  }

  public int getId(){ return this.id; }

  public void setId(int id){ this.id = id; }

  public Book getBook(){return this.book;}

  public void setBook(Book book){ this.book = book; }

  public Client getClient(){ return  this.client; }

  public void setClient(Client client){ this.client = client; }

  public String getIssueDate(){ return this.issueDate; }

  public void setIssueDate(String issueDate){ this.issueDate = issueDate; }

  public String getDueDate(){ return this.dueDate; }
  private String setDueDate(){
    String[] tokens = this.issueDate.split("/");
    String date = tokens[0];
    String month = tokens[1];
    String year = tokens[2];
    Integer dateInt = Integer.parseInt(date);
    Integer monthInt = Integer.parseInt(month);
    Integer yearInt = Integer.parseInt(year);

    if (monthInt == 12){
      monthInt = 1;
      yearInt++;
    }else{
      monthInt++;
    }

    if(dateInt == 31){
      dateInt = 1;
      monthInt++;
    }

    String dueDate = String.join("/", dateInt.toString(), monthInt.toString(), yearInt.toString());

    return dueDate;
  }

}
