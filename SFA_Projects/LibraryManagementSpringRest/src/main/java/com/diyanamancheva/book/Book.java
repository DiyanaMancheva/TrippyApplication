package com.diyanamancheva.book;

public class Book {
  private int id;
  private String title;
  private int authorId;
  private String publishingDate;


  public Book(String title, int authorId, String publishingDate){
    this.title = title;
    this.authorId = authorId;
    this.publishingDate = publishingDate;
  }

  public Book(int id, String title, int authorId, String publishingDate){
    this(title, authorId, publishingDate);
    this.id = id;
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

  public int getAuthorId() {
    return authorId;
  }

  public void setAuthorId(int authorId) {
    this.authorId = authorId;
  }

  public String getPublishingDate() {
    return this.publishingDate;
  }

  public void setPublishingDate(String publishingDate) {
    this.publishingDate = publishingDate;
  }

  @Override
  public String toString() {
    return "Book: \n" +
           "Id = " + id + "\n" +
           "Tile = " + title + "\n" +
           "AuthorId = " + authorId;
  }
}
