package com.diyanamancheva.book;

import com.diyanamancheva.author.Author;
import com.diyanamancheva.client.Client;

import java.util.ArrayList;

public class Book {
  private int id;
  private String name;
  private Author author;
  private String publishingDate;
  private Client client;

  public Book(String name, Author author, String publishingDate){
    this.name = name;
    this.author = author;
    this.publishingDate = publishingDate;
  }

  public boolean isAvailable = true;

  public int getId() {
    return id;
  }

  public void setId(int id) { this.id = id; }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public String getPublishingDate() {
    return this.publishingDate;
  }

  public void setPublishingDate(String publishingDate) {
    this.publishingDate = publishingDate;
  }

  public Client getClient(){ return this.client; }

  public void setClient(Client client) { this.client = client; }

}
