package com.diyanamancheva.client;

import com.diyanamancheva.book.Book;
import java.util.ArrayList;

public class Client {
  private int id;

  private String name;

  private ArrayList<Book> books;

  public Client(String name){
    this.name = name;
    this.books = new ArrayList<Book>();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ArrayList<Book> getBooks(){
    return books;
  }
}
