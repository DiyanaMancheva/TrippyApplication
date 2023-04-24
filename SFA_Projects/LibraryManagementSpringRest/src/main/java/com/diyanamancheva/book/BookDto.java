package com.diyanamancheva.book;

public class BookDto {
  private int id;
  private String title;
  private int authorId;
  private String publishingDate;

  public BookDto(int id, String title, int authorId, String publishingDate) {
    this.id = id;
    this.title = title;
    this.authorId = authorId;
    this.publishingDate = publishingDate;
  }

  public int getId() {
    return id;
  }

  public void setId ( int id) {
    this.id = id;
  }

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
    return publishingDate;
  }

  public void setPublishingDate(String publishingDate) {
    this.publishingDate = publishingDate;
  }
}
