package com.diyanamancheva.author;

import com.diyanamancheva.book.Book;

import java.util.ArrayList;

public class Author {
  private int id;

  private String name;

  private ArrayList<Book> books;

  public Author(String name){
    this.name = name;
    this.books = new ArrayList<Book>();
  }

  public int getId() { return id; }

  public void setId(int id) { this.id = id;}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ArrayList<Book> getBooks(){
    return this.books;
  }
}
