package com.diyanamancheva.book;

import com.diyanamancheva.author.Author;

public class Book {
  private int id;
  private String title;
  private Author author;
  private String publishingDate;


  public Book(String title, Author author, String publishingDate){
    this.title = title;
    this.author = author;
    this.publishingDate = publishingDate;
  }

  public boolean isAvailable = true;

  public int getId() {
    return id;
  }

  public void setId(int id) { this.id = id; }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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
}
